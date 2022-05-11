package com.deepseat.ds.vo

class MenuVO {

    var id: String
    var title: String
    var desc: String?
    var accessory: Boolean = true
    var icon: Int

    constructor(id: String, title: String, desc: String?, accessory: Boolean, icon: Int) {
        this.id = id
        this.title = title
        this.desc = desc
        this.accessory = accessory
        this.icon = icon
    }

}