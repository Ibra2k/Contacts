package org.example.project.domain

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.util.Date

class Contact: RealmObject{
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var fullName: String = ""
    var nickName: String = ""
    var phoneNumber: String = ""
    var business: String = ""
    var isChecked: Boolean = false
}

class RecentCall : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()  // Unique identifier for each call
    var phoneNumber: String = ""    // Stores the number that was called
    @Ignore
    var callTime: Date = Date()     // Timestamp of the call
    var isChecked: Boolean = false

}
