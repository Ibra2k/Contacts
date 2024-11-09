package org.example.project.data

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.example.project.domain.Contact
import org.example.project.domain.RecentCall
import org.example.project.domain.RequestState

class MongoDB {
    private var realm: Realm? = null

    init {
        realmConfiguration()
    }

    private fun realmConfiguration() {
        val config = RealmConfiguration.create(
            schema = setOf(Contact::class, RecentCall::class)
        )
        realm = Realm.open(config)
    }


    fun readRecentCalls(): Flow<RequestState<List<RecentCall>>> {
        return realm?.query<RecentCall>()
            ?.asFlow()
            ?.map { result ->
                RequestState.Success(data = result.list)
            } ?:
            flow { RequestState.Error("Realm not available") }
    }

    fun readContacts(): Flow<RequestState<List<Contact>>> {
        return realm?.query<Contact>(query = "isChecked == $0", false)
            ?.asFlow()
            ?.map { result ->
                RequestState.Success(data = result.list)
            } ?:
            flow { RequestState.Error("Realm is not available") }
    }

    suspend fun insertRecent(recent: RecentCall){
        realm?.write { copyToRealm(recent) }
    }

    suspend fun insertContact(contact: Contact){
        realm?.write { copyToRealm(contact) }
    }

    suspend fun updateContact(contact: Contact){
        realm?.write {
            val queryContact = query<Contact>("_id == $0", contact._id).first().find()
            queryContact?.let {
                findLatest(it)?.let { currContact ->

                    currContact.fullName = contact.fullName
                    currContact.nickName = contact.nickName
                    currContact.business = contact.business
                    currContact.phoneNumber = contact.phoneNumber
                }
            }
        }
    }

    suspend fun deleteContact(contact: List<Contact>){
        realm?.write {
            val contactToDelete = query<Contact>("_id == $0", contact.map { it._id }).find()
            delete(contactToDelete)
        }
    }

    suspend fun deleteRecent(contact: List<RecentCall>){
        realm?.write {
            val contactToDelete = query<RecentCall>("_id == $0", contact.map { it._id }).find()
            delete(contactToDelete)
        }
    }
}