package org.example.project.domain

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Contact: RealmObject{
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var fullName: String = ""
    var nickName: String = ""
    var phoneNumber: String = ""
    var business: String = ""
    var isChecked: Boolean = false
}
