package com.daangn.www.livedatasamples

import androidx.lifecycle.*
import java.text.DecimalFormat

class TestViewModel: ViewModel() {

    //MediatorLiveData Example
    private val leftOperand = MutableLiveData<Int>()
    private val rightOperand = MutableLiveData<Int>()

    private val _plusMediator = MediatorLiveData<Int>()
    val plusMediator: LiveData<Int>
        get() = _plusMediator

    //Transformations Map Example
    private val _price = MutableLiveData<Int>()

    val formattedPrice: LiveData<String> = _price.map {
        DecimalFormat("#,###").format(it)
    }

    //Transformations switchMap Example
    private val _phoneNumber = MutableLiveData<String>()
    val user: LiveData<User> = Transformations.switchMap(_phoneNumber) { phone ->
        getUserByPhone(phone)
    }

    //MediatorLiveData Example
    init {
        _plusMediator.addSource(leftOperand) {
            _plusMediator.value = plusOperands()
        }

        _plusMediator.addSource(rightOperand) {
            _plusMediator.value = plusOperands()
        }
    }

    fun setPhoneNumber(price: String) {
        _phoneNumber.value = price
    }

    fun setPrice(price: Int) {
        _price.value = price
    }

    fun setLeftOperand(leftValue: Int) {
        leftOperand.value = leftValue
    }

    fun setRightOperand(rightValue: Int) {
        rightOperand.value = rightValue
    }

    private fun plusOperands(): Int
        = (leftOperand.value ?: 0) + (rightOperand.value ?: 0)

    private fun getUserByPhone(phoneNumber: String) : LiveData<User> {
        //더미로 데이터를 만들어서 가져옴.
        //실제로는 DB혹은 Network에서 가져온 데이터가 될 수 있음.
        return when (phoneNumber) {
            "123-4567" -> {
                MutableLiveData<User>().apply {
                    value = User(1, "123-4567", "홍길동")
                }
            }
            "765-4321" -> {
                MutableLiveData<User>().apply {
                    value = User(2, "765-4321", "오서방")
                }
            }
            else -> {
                MutableLiveData<User>().apply {
                    value = User(3, "000-0000", "아무게")
                }
            }
        }
    }
}