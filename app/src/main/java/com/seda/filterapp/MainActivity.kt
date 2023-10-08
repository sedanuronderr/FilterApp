package com.seda.filterapp

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.seda.adapter.EmployeeListAdapter
import com.seda.filterapp.databinding.ActivityMainBinding
import com.seda.options.FilterAlertDialog
import com.seda.options.FiltersEnum
import com.seda.room.Employee
import java.util.EventListener
import java.util.Objects

class MainActivity : AppCompatActivity(), com.seda.EventListener {
    private var binding: ActivityMainBinding? = null
    lateinit var filterAlertDialog: FilterAlertDialog
    lateinit var context: Context
    lateinit var eventListener: com.seda.EventListener
    lateinit var adapter: EmployeeListAdapter
    var fullEmployeeArrayList: MutableList<Employee> = mutableListOf()
    var filterEmployeeArrayList: MutableList<Employee> = mutableListOf()
    var deger: MutableList<Employee> = mutableListOf<Employee>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        setSupportActionBar(binding!!.toolbar)
        Objects.requireNonNull(supportActionBar)?.setHomeAsUpIndicator(R.drawable.ic_logo)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        context = this
        eventListener = this

        adapter= EmployeeListAdapter(mutableListOf(),eventListener)
        binding!!.employeeRv.adapter=adapter


        binding!!.addEmployeeBtn.setOnClickListener(View.OnClickListener {

            checkExistEmployee(
                binding?.employeeNameEt?.text.toString(),
                binding?.employeeSalaryEt?.text.toString()
            )
            binding?.employeeNameEt?.setText("")
            binding?.employeeSalaryEt?.setText("")
        })
        binding?.clearData?.setOnClickListener(View.OnClickListener { deleteAll() })


        binding?.options?.setOnClickListener(View.OnClickListener {
            filterAlertDialog = FilterAlertDialog(context, eventListener)
            filterAlertDialog.show()
        })


        initData()
    }

    override fun deleteEmployee(id: Int) {
    }

    override fun chooseOption(filtersEnum: FiltersEnum) {
        when(filtersEnum){
            FiltersEnum.ALL -> {
                binding?.filterTv?.text = resources.getString(R.string.filter_all)
                getAll()
            }
            FiltersEnum.NAME -> {
                val name: String = filtersEnum.employeeName
                binding?.filterTv?.text = resources.getString(R.string.filter_name, name)
           getEmployeeEqualName(name)

            }
            FiltersEnum.PART_NAME -> {
                val partName: String = filtersEnum.employeeName
                binding?.filterTv?.text = resources.getString(R.string.filter_part_name, partName)
                getEmployeeContainsPartName(partName)

            }
            FiltersEnum.SALARY_LESS -> {
                val maxSalary: Float = filtersEnum.maxSalary
                binding?.filterTv?.text = resources.getString(R.string.filter_less_than, maxSalary)

                getEmployeeLessThan(maxSalary)

            }
            FiltersEnum.SALARY_MORE -> {
                val minSalary: Float = filtersEnum.minSalary
                binding?.filterTv?.text = resources.getString(R.string.filter_more_than, minSalary)
                getEmployeeMoreThan(minSalary)


            }

            else -> {}
        }



    }


    fun initData() {
        binding?.addEmployeeBtn?.isEnabled = false
        binding?.clearData?.isEnabled = false
         deger = mutableListOf<Employee>(
            Employee("John Loyd", 112.4f),
            Employee("Bibi Bob", 8852.43f)


        )
        deger.forEach {
            insertEmployee(it)

        }

        binding?.addEmployeeBtn?.isEnabled = true
        binding?.clearData?.isEnabled = true
        binding?.filterTv?.text = resources.getString(R.string.filter_all)
    }



    private fun checkExistEmployee(name: String, salary: String) {
        val employeeName = getName(name)
        val employeeSalary = getSalary(salary)
        if (fullEmployeeArrayList.none { employee ->
                employee.name == employeeName && employee.salary == employeeSalary }) {
            addEmployee(name, salary)
        } else {
            createExistEmployeeDialog()
        }



    }
    private fun addEmployee(name: String, salary: String) {
        val employeeName = getName(name)
        val employeeSalary = getSalary(salary)
        val newEmployee: Employee = Employee(employeeName, employeeSalary)

        deger.add(newEmployee)

        insertEmployee(newEmployee)


    }


    fun insertEmployee(employee: Employee) {
        binding?.addEmployeeBtn?.isEnabled = false

       updateScreen(deger)

    }
    fun deleteAll() {
        //Write code and add filters here
        filterEmployeeArrayList = mutableListOf()
        filterEmployeeArrayList.add(createNewEmployee("asd", 123.0f))
        updateScreen(filterEmployeeArrayList)
    }
    fun updateScreen(employeeList: List<Employee>) {
        adapter.updateEmployeeListItems(employeeList)
        adapter.notifyDataSetChanged()
        binding?.addEmployeeBtn?.isEnabled = true
    }

    private fun getSalary(salary: String): Float {
        return if (salary.isEmpty()) {
            0f
        } else {
            salary.toFloat()
        }
    }
    fun getAll() {
        //Write code and add filters here

        updateScreen(deger)
    }

    fun getEmployeeEqualName(name: String) {
        //Write code and add filters here
        val filteredList = ArrayList<Employee>()
        val filterEmployeeArrayList = deger.filter{
            it.name.equals(name,true)

        }
        updateScreen(filterEmployeeArrayList)
    }

    fun getEmployeeContainsPartName(partName: String?) {
        //Write code and add filters here
        val charc: Char = partName?.single()!!
        val filterEmployeeArrayList = deger.filter{
            it.name.contains(charc,true)

        }
        updateScreen(filterEmployeeArrayList)
    }

    fun getEmployeeLessThan(maxSalary: Float) {
        //Write code and add filters here
        val filterEmployeeArrayList = deger.filter{
            it.salary > maxSalary

        }
        updateScreen(filterEmployeeArrayList)
    }

    fun getEmployeeMoreThan(minSalary: Float) {
        //Write code and add filters here

        val filterEmployeeArrayList = deger.filter{
            it.salary < minSalary

        }
        updateScreen(filterEmployeeArrayList)
    }

    private fun getName(name: String): String {
        return if (name.isEmpty()) {
            "Unnamed Employee"
        } else {
            name
        }
    }

    private fun createNewEmployee(name: String, salary: Float): Employee {
        return Employee(name, salary)
    }
    private fun createExistEmployeeDialog() {
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.exist_dialog_title))
            .setMessage(resources.getString(R.string.dialog_message))
            .setCancelable(false)
            .setPositiveButton(
                android.R.string.yes
            ) { dialog, which -> dialog.dismiss() }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
}