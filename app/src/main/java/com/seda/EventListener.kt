package com.seda

import com.seda.options.FiltersEnum

interface EventListener {
    fun deleteEmployee(id:Int)
    fun chooseOption(filtersEnum:FiltersEnum)
}