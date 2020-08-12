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


class SolveQuizAdapter(val context: Context, val solve_quiz_list : ArrayList<Quiz>) :
    RecyclerView.Adapter<SolveQuizAdapter.holder>() {

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

        fun bind (quiz: Quiz, index : Int) {
            // 질문이 없는 경우
            if (quiz.content == "") {
                tv_content?.setText("응 질문없어~")
            } else {
                tv_content?.setText(quiz.content)
            }

            // O 버튼 클릭 리스너
            iv_yes.setOnClickListener{
                //answer=1로 바꾸기(yes활성화)
                if(quiz.answer != 1){
                    iv_yes.setImageResource(R.drawable.img_o_act)
                    iv_no.setImageResource(R.drawable.img_x_not)
                    quiz.answer = 1
                } else{     // yes를 비활성화
                    iv_yes.setImageResource(R.drawable.img_o)
                    quiz.answer = 0

                }
            }
            // X 버튼 클릭 리스너
            iv_no.setOnClickListener{
                ///answer=0로 바꾸기(no활성화)
                if(quiz.answer != 0){
                    iv_no.setImageResource(R.drawable.img_x)
                    iv_yes.setImageResource(R.drawable.img_o)
                    quiz.answer = 1
                } else{     // no 비활성화
                    iv_no.setImageResource(R.drawable.img_x_not)
                    quiz.answer = 0
                }

            }
        }
    }
}
