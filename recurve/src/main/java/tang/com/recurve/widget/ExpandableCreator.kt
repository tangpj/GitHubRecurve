/*
 * Copyright (C) 2018 Tang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tang.com.recurve.widget

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by tang on 2018/3/15.
 * 辅助创建二级Adapter
 */

class ExpandableCreator<Parent,Child,ParentHolder: RecyclerView.ViewHolder
        , ChildHolder: RecyclerView.ViewHolder>
@JvmOverloads constructor(private val adapter: ModulesAdapter, private val itemType: Int = 0)
    :Creator,ExpandableOperator<Parent,Child> {

    private var dataMap: LinkedHashMap<Parent,MutableList<Child>> = LinkedHashMap()

    override fun setDataList(dataMap: LinkedHashMap<Parent, MutableList<Child>>) {
        this.dataMap = dataMap
        adapter.notifyModulesItemSetChange(this)
    }

    override fun getData(): LinkedHashMap<Parent, MutableList<Child>> = LinkedHashMap(dataMap)

    override fun addParentItem(parent: Parent): List<Child>? {
        val child = dataMap.put(parent, mutableListOf())
        adapter.notifyModulesItemInserted(this, getItemCount() - 1)
        return child
    }

    override fun addParentItem(parentPosition: Int, parent: Parent): List<Child>? {
       return realSetParentItem(parentPosition,parent,true)
    }

    override fun setParentItem(parentPosition: Int, parent: Parent): List<Child>?{
        return realSetParentItem(parentPosition,parent)
    }

    override fun removedParentItem(parent: Parent) {
        val childList = dataMap[parent]
        val aimsStartPosition = getParentPositionInCreator(parent)
        val aimsEnePosition = aimsStartPosition + (childList?.size ?: 0)
        dataMap.remove(parent)
        adapter.notifyModulesItemRangeRemoved(this,aimsStartPosition,aimsEnePosition)
    }

    override fun removedParentItemAt(parentPosition: Int){
        removedParentItem(getParent(parentPosition))
    }

    override fun addChildItem(parent: Parent, child: Child): Boolean
            = operatorChildItemByParent(parent,child){
        operatorChild, childList ->
        val result = childList.add(operatorChild)
        adapter.notifyModulesItemInserted(this, getChildPositionInCreator(parent,child))
        result
    }

    override fun addChildItem(parentPosition: Int, child: Child): Boolean
            = operatorChildItemByParentPosition(parentPosition,child){
        operatorChild, childList ->
        val result = childList.add(operatorChild)
        adapter.notifyModulesItemInserted(this, getChildPositionInCreatorAt(parentPosition,child))
        result
    }

    override fun addChildItem(parent: Parent, childPosition: Int, child: Child)
            = operatorChildItemByParent(parent,child) {
        operatorChild, childList ->
        childList.add(childPosition,operatorChild)
        adapter.notifyModulesItemInserted(this, getChildPositionInCreator(parent,child))
    }

    override fun addChildItem(parentPosition: Int, childPosition: Int, child: Child)
            = operatorChildItemByParentPosition(parentPosition,child){
        operatorChild, childList ->
        childList.add(childPosition,operatorChild)
        adapter.notifyModulesItemInserted(this, getChildPositionInCreatorAt(parentPosition,child))
    }

    override fun setChildItem(parent: Parent, childPosition: Int, child: Child): Child
            = operatorChildItemByParent(parent,child) {
        operatorChild, childList ->
        childList.set(childPosition,operatorChild)
    }

    override fun setChildItem(parentPosition: Int, childPosition: Int, child: Child): Child
            = operatorChildItemByParentPosition(parentPosition,child){
        operatorChild, childList -> childList.set(childPosition,operatorChild)
    }

    override fun removedChildItem(parent: Parent, child: Child): Boolean {
        val childList = dataMap[parent] ?: throw NullPointerException("can't not find parent: $parent")
        val isSucceed = childList.remove(child)
        adapter.notifyModulesItemRemoved(this,getChildPositionInCreator(parent,child))
        return isSucceed
    }

    override fun removedChildItem(parentPosition: Int, child: Child): Boolean {
        val parent = getParent(parentPosition)
        val childList = dataMap[parent]
                ?: throw NullPointerException("can't not find parent in position $parentPosition")
        val isSucceed = childList.remove(child)
        adapter.notifyModulesItemInserted(this, getChildPositionInCreator(parent,child))
        return isSucceed
    }

    override fun removedChildItemAt(parent: Parent, childPosition: Int): Child {
        val childList = dataMap[parent] ?: throw NullPointerException("can't not find parent: $parent")
        val result = childList.removeAt(childPosition)
        adapter.notifyModulesItemRemoved(this,getChildPositionInCreator(parent,childPosition))
        return result
    }

    override fun removedChildItemAt(parentPosition: Int, childPosition: Int): Boolean {

    }

    override fun getParentItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getChildItemCountByParent(parentPosition: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemViewType(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSpan(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindItemView(itemHolder: RecyclerView.ViewHolder, inCreatorPosition: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun realSetParentItem(parentPosition: Int, parent: Parent,isAdd: Boolean = false): List<Child>?{
        val operatorMap  = LinkedHashMap<Parent,MutableList<Child>>()
        var result: MutableList<Child>? = null
        if (dataMap.size < parentPosition){
            dataMap.entries.forEachIndexed{ index, (p, child) ->
                if (index == parentPosition){
                    result = operatorMap.put(parent, mutableListOf())
                    if (isAdd) operatorMap[p] = child
                }else{
                    operatorMap[p] = child
                }
            }
        }else{
            throw IndexOutOfBoundsException("Invalid index $parentPosition, size is ${dataMap.size}")
        }
        dataMap = operatorMap
        adapter.notifyModulesItemInserted(this,getParentPositionInCreator(parent))
        return result?.toList()
    }

    private fun <R> operatorChildItemByParentPosition(
            parentPosition: Int, child: Child, operator: (operatorChild: Child, childList: MutableList<Child>) -> R): R{
        val parent = getParent(parentPosition)
        val childList = dataMap[parent]
                ?: throw NullPointerException("can't not find parent in position $parentPosition")
        return  operator.invoke(child,childList)
    }

    private fun <R> operatorChildItemByParent(
            parent: Parent, child: Child, operator: (operatorChild: Child, childList: MutableList<Child>) -> R): R{
        val childList = dataMap[parent] ?: throw NullPointerException("can't not find parent: $parent")
        return operator.invoke(child,childList)
    }

    private fun getParentPositionInCreator(mParent: Parent): Int{
        var parentPosition = 0
        dataMap.entries.forEach { (parent, child) ->
            if (mParent == parent) return@forEach
            else parentPosition += child.size + 1
        }
        return parentPosition
    }

    private fun getParent(parentPosition: Int): Parent{
        if (parentPosition < dataMap.size){
            dataMap.entries.forEachIndexed{index, mutableEntry ->
                if (index == parentPosition){
                    return mutableEntry.key
                }
            }
        }
        throw IndexOutOfBoundsException("Invalid index $parentPosition, size is ${dataMap.size}")
    }

    private fun getChildPositionInCreator(mParent: Parent, child: Child): Int{
        val childPositionInList: Int = dataMap[mParent]?.indexOf(child) ?: 0
        return getParentPositionInCreator(mParent) + childPositionInList + 1
    }

    private fun getChildPositionInCreatorAt(parentPosition: Int, child: Child): Int =
        getChildPositionInCreator(getParent(parentPosition),child)

    private fun getChildPositionInCreator(mParent: Parent, childPositionInList: Int): Int
            = getParentPositionInCreator(mParent) + childPositionInList + 1

    private fun getChildPositionInCreatorAt(parentPosition: Int, childPositionInList: Int): Int =
            getChildPositionInCreator(getParent(parentPosition),childPositionInList)

//    fun onCreateParentViewHolder(parent: ViewGroup): RecyclerView.ViewHolder{}
//
//    fun onCreateChildViewHolder(parent: ViewGroup): RecyclerView.ViewHolder{}

    fun onBindParentItemView(parentHolder: ParentHolder, parentPosition: Int){}

    fun onBindChildItemView(childHolder: ChildHolder, childPosition: Int){}

//    abstract fun onBindParentItemView(parentHolder: ParentHolder, parent: Parent, parentPosition: Int)
//
//    abstract fun onBindChildItemView(childHolder: ChildHolder, child: Child, childPosition: Int)
}