package com.daangn.www.livedatasamples

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

inline fun <X,Y> LiveData<X>.map(crossinline block: (X) -> Y): LiveData<Y> {
    return MediatorLiveData<Y>().apply {
        addSource(this@map) {
            this.value = block.invoke(it)
        }
    }
}

inline fun <X, Y> LiveData<X>.switchMap(crossinline block: (X?) -> LiveData<Y>): LiveData<Y> {
    return MediatorLiveData<Y>().apply {
        addSource(this@switchMap, object : Observer<X> {
            var source: LiveData<Y>? = null
            override fun onChanged(x: X?) {
                val newLiveData = block.invoke(x)
                if (source === newLiveData) {
                    return
                }
                source?.let { source ->
                    removeSource(source)
                }
                source = newLiveData
                source?.let { source ->
                    addSource(source) { y -> value = y }
                }
            }
        })
    }
}

inline fun EditText.afterTextChanged(crossinline block: (editable: Editable?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            block.invoke(p0)
        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    })
}
