package com.sun.sqlite_contentprovider

import android.app.Activity

fun Activity.showMessageDialog(message: String,
                               cancelable: Boolean = false,
                               onPositiveButtonClicked: () -> Unit = {}) {
    AlertDialogUtil.showMessageDialog(this, message, cancelable, onPositiveButtonClicked)
}