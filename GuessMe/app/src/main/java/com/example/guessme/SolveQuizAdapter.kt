package com.example.guessme

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.guessme.data.Quiz


class SolveQuizAdapter(val context: Context, val solve_quiz_list : ArrayList<Quiz>) : RecyclerView.Adapter<SolveQuizAdapter.holder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): holder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_solve_quiz,p0,false)
        return holder(view)
    }
    override fun getItemCount(): Int { return solve_quiz_list.size }
    override fun onBindViewHolder(p0: holder, p1: Int) { p0.bind(solve_quiz_list[p1],p1) }

    inner class holder(view : View) : RecyclerView.ViewHolder(view){

        val tv_content = view.findViewById<TextView>(R.id.solve_quiz_content)
        val iv_yes = view.findViewById<ImageView>(R.id.img_sq_o)
        val iv_no = view.findViewById<ImageView>(R.id.img_sq_x)

        fun bind(quiz : Quiz, index : Int){
            tv_content.text = quiz.content


            iv_yes.setOnClickListener{
//                if (!state) 인경우 state = 1로 바꾸고 x버튼 비활성화, o버튼 활성화
//                submit_list에 quizId, answer 넘겨줌
            }
            iv_no.setOnClickListener{
//                if (state) 인경우 state = 0로 바꾸고 x버튼 활성화, o버튼 비활성화
//                submit_list에 quizId, answer 넘겨줌

            }
        }
    }
}

//class SolveQuizAdapter(val context: Context) : RecyclerView.Adapter<SolveQuizAdapter.holder>() {
//    var res_state : Boolean = false
//    var quizlist = ArrayList<Quiz>()
//    val textWatcher_ary = arrayListOf<EditListener>()
//    constructor(context : Context, res_state : Boolean) : this(context){
//        this.res_state = res_state
//    }
//
//    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): holder {
//        val view = LayoutInflater.from(context).inflate(R.layout.list_item_solve_quiz,p0,false)
//        for(i in 0 until )
//            textWatcher_ary.add(EditListener(i))
//        return holder(view)
//    }
//
//    override fun getItemCount(): Int { return quizlist.size }
//    override fun onBindViewHolder(p0: holder, p1: Int) { p0.bind(context, quizlist[p1].content,p1)}
//
//    inner class holder(view: View) : RecyclerView.ViewHolder(view) {
//        val childEditText = view.findViewById<EditText>(R.id.res_child_name_EditText)
//        val indexTextView = view.findViewById<TextView>(R.id.res_child_index_TextView)
//
//        val solve_quiz_content = view.findViewById<TextView>(R.id.quiz_content)
//        val
//
//        fun bind(context: Context, child : String, index: Int){
//            indexTextView.text = (index + 1).toString()
//            if(res_state)
//                childEditText.isEnabled = false
//            if(index < itemCount) {
//                childEditText.setText(quizlist[index].c_name)
//            }
//            for(i in 0 until itemCount)
//                childEditText.removeTextChangedListener(textWatcher_ary[i])
//            childEditText.addTextChangedListener(textWatcher_ary[index])
//        }
//    }
//    fun quizlist():Boolean{
//        var str : String = ""
//        var count = 0
//        for(i in 0 until quizlist.size){
//            if(quizlist[i].c_name.isEmpty()){
//                str = str + (i + 1).toString() + ", "
//                count++
//            }
//            if(count >= 5){
//                Toast.makeText(context,"10개 이상의 칸이 비어있습니다",Toast.LENGTH_SHORT).show()
//                return false
//            }
//        }
//        if(str != ""){
//            str = str.substring(0, str.lastIndex - 1)
//            Toast.makeText(context,"$str 번째 이름을 입력해주세요",Toast.LENGTH_SHORT).show()
//            return false
//        }
//        return true
//    }
//    fun list_init(size : Int){
//        if(size > 5){
//            Toast.makeText(context,"최대 피보호자 수는 100명 입니다",Toast.LENGTH_SHORT).show()
//            return
//        }
//        if(childlist.size < size){
//            for(i in childlist.size until size){
//                childlist.add(Child())
//                notifyItemChanged(i)
//            }
//        }
//        else if(size < childlist.size){
//            while (size != childlist.size){
//                notifyItemRemoved(childlist.lastIndex)
//                childlist.removeAt(childlist.lastIndex)
//            }
//        }
//        Log.d("Res_Group", childlist.toString())
//    }
//
//    fun setlist(list : ArrayList<Quiz>){
//        quizlist = list
//        notifyDataSetChanged()
//    }
//    fun getlist():ArrayList<Quiz>{return quizlist}
//
//    inner class EditListener(var index: Int) : TextWatcher {
//        override fun afterTextChanged(s: Editable?) {
//            if(!s.toString().isEmpty()) {
//                if(index < itemCount)
//                    quizlist[index].c_name = s.toString()
//            }
//            else {
//                childlist[index].c_name = ""
//            }
//        }
//        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
//        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//        }
//    }
//}