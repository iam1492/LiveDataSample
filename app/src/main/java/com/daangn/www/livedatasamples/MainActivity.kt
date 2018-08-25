package com.daangn.www.livedatasamples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.daangn.www.livedatasamples.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this).get(TestViewModel::class.java)

        viewBinding.price.afterTextChanged {
            val value = if (it?.toString()?.isNotBlank()  == true) it.toString().toInt() else 0
            viewModel.setPrice(value)
        }

        viewModel.formattedPrice.observe(this, Observer {
            viewBinding.priceResult.text = it
        })

        viewBinding.leftOperand.afterTextChanged {
            val value = if (it?.toString()?.isNotBlank()  == true) it.toString().toInt() else 0
            viewModel.setLeftOperand(value)
        }

        viewBinding.rightOperand.afterTextChanged {
            val value = if (it?.toString()?.isNotBlank()  == true) it.toString().toInt() else 0
            viewModel.setRightOperand(value)
        }

        viewModel.plusMediator.observe(this, Observer {
            viewBinding.plusResult.text = it.toString()
        })

        viewModel.user.observe(this, Observer {
            viewBinding.userResult.text = "이름: ${it.name}   전화번호: ${it.phoneNumber}"
        })

        viewModel.setPhoneNumber("123-4567")
        viewModel.setPhoneNumber("765-4321")
    }
}
