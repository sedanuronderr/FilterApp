package com.seda.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.seda.EventListener
import com.seda.filterapp.databinding.ItemEmployeeBinding
import com.seda.room.Employee

class EmployeeListAdapter(private val employeeList: MutableList<Employee>,
                          private val listener: EventListener
):RecyclerView.Adapter<EmployeeListAdapter.EmployeeViewHolder>() {


  class EmployeeViewHolder(val binding: ItemEmployeeBinding,val listener: EventListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(employee: Employee, position: Int) {
            binding.employeeNameText.text = employee.name
            binding.employeeSalaryText.text = employee.salary.toString()
            binding.deleteEmployeeBtn.setOnClickListener { v -> listener.deleteEmployee(position) }
        }

    }


    fun updateEmployeeListItems(employees: List<Employee>) {
        val diffCallback = EmployeeCallback(employeeList, employees)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        employeeList.clear()
        employeeList.addAll(employees)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmployeeListAdapter.EmployeeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemEmployeeBinding = ItemEmployeeBinding.inflate(layoutInflater, parent, false)
        return EmployeeViewHolder(binding, listener)

    }

    override fun onBindViewHolder(holder: EmployeeListAdapter.EmployeeViewHolder, position: Int) {
            holder.bind(employeeList[position],position)

    }

    override fun getItemCount(): Int {

       return employeeList.size

    }


}

internal class EmployeeCallback(
    private val mOldEmployeeList: List<Employee>,
    private val mNewEmployeeList: List<Employee>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldEmployeeList.size
    }

    override fun getNewListSize(): Int {
        return mNewEmployeeList.size

    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldEmployeeList[oldItemPosition].name == mNewEmployeeList[newItemPosition].name
                && mOldEmployeeList[oldItemPosition].salary == mNewEmployeeList[newItemPosition].salary


    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        val oldEmployee = mOldEmployeeList[oldItemPosition]
        val newEmployee = mNewEmployeeList[newItemPosition]
        return oldEmployee.name == newEmployee.name

    }


}