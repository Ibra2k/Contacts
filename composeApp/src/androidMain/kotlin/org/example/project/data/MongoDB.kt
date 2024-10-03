package org.example.project.data

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.example.project.domain.Contact
import org.example.project.domain.RequestState

class MongoDB {
    private var realm: Realm? = null

    init {
        realmConfiguration()
    }

    private fun realmConfiguration() {
        val config = RealmConfiguration.create(
            schema = setOf(Contact::class)
        )
        realm = Realm.open(config)
    }

    fun readContacts(): Flow<RequestState<List<Contact>>> {
        return realm?.query<Contact>(query = "isChecked == $0", false)
            ?.asFlow()
            ?.map { result ->
                RequestState.Success(data = result.list)
            } ?:
            flow { RequestState.Error("Realm is not available") }
    }

    suspend fun insertContact(contact: Contact){
        realm?.write { copyToRealm(contact) }
    }

    suspend fun updateContact(contact: Contact){
        realm?.write {
            val queryContact = query<Contact>("_id == $0", contact._id).first().find()
            queryContact?.let {
                findLatest(it)?.let { currContact ->
                    currContact.apply {
                        fullName = contact.fullName
                        nickName = contact.nickName
                        business = contact.business
                        phoneNumber = contact.phoneNumber
                    }
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
}