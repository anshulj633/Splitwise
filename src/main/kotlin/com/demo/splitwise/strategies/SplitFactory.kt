package com.demo.splitwise.strategies

import com.demo.splitwise.transactions.enums.SplitType

class SplitFactory {
    companion object{
        fun getSplitTypeObject(splitType: SplitType, userName: String): Split{
            return when(splitType){
                SplitType.Equal -> EqualSplit(userName)
                SplitType.Manual -> ManualSplit(userName)
                SplitType.Percent -> PercentSplit(userName)
            }
        }
    }

}