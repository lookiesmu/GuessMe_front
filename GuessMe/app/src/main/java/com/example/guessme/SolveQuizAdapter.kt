package com.example.guessme

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guessme.adapters.CreateQuizAdapter
import com.example.guessme.data.Quiz
import com.example.guessme.data.SolveAnswer


class SolveQuizAdapter(val context: Context, val solveQuizList : ArrayList<Quiz>, val myAnswerList : ArrayList<SolveAnswer>) :
    RecyclerView.Adapter<SolveQuizAdapter.SolveQuizholder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SolveQuizholder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_solve_quiz,p0,false)
        return SolveQuizholder(view)
    }

    override fun getItemCount(): Int { return solveQuizList.size }


    override fun onBindViewHolder(holder: SolveQuizAdapter.SolveQuizholder, position: Int) {
        holder?.bind(solveQuizList[position], position)
        Log.e("리사이클러뷰 1",solveQuizList[0].toString())
        Log.e("리사이클러뷰 2",solveQuizList[1].toString())
        Log.e("리사이클러뷰 3",solveQuizList[2].toString())
        Log.e("리사이클러뷰 4",solveQuizList[3].toString())
        Log.e("리사이클러뷰 5",solveQuizList[4].toString())

    }


    inner class SolveQuizholder(view : View) : RecyclerView.ViewHolder(view){

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
                    quiz.answer = -1

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
                    quiz.answer = -1
                }

            }
        }
    }
}
