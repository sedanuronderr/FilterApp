package com.seda.filterapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.seda.filterapp.databinding.ActivityMainBinding
import com.seda.options.FilterAlertDialog
import com.seda.options.FiltersEnum
import java.util.EventListener
import java.util.Objects

class MainActivity : AppCompatActivity(), com.seda.EventListener {
    private var binding: ActivityMainBinding? = null
    lateinit var filterAlertDialog: FilterAlertDialog
    lateinit var context: Context
    lateinit var eventListener: com.seda.EventListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)



        setSupportActionBar(binding!!.toolbar)
        Objects.requireNonNull(supportActionBar)?.setHomeAsUpIndicator(R.drawable.ic_logo)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        context = this
        eventListener = this

        binding?.options?.setOnClickListener(View.OnClickListener {
            filterAlertDialog = FilterAlertDialog(context, eventListener)
            filterAlertDialog.show()
        })
    }

    override fun deleteEmployee(id: Int) {
    }

    override fun chooseOption(filtersEnum: FiltersEnum) {




    }
}