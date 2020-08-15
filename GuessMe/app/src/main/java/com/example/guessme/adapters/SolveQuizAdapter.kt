package com.example.guessme.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guessme.R
import com.example.guessme.data.Quiz


class SolveQuizAdapter(val context: Context, val solveQuizList : ArrayList<Quiz>, val myAnswerList : ArrayList<Int>) :
    RecyclerView.Adapter<SolveQuizAdapter.SolveQuizholder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SolveQuizholder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_solve_quiz,p0,false)
        return SolveQuizholder(view)
    }

    override fun getItemCount(): Int { return solveQuizList.size }


    override fun onBindViewHolder(holder: SolveQuizholder, position: Int) {
        holder?.bind(solveQuizList[position], position)
        Log.e("리사이클러뷰 1",solveQuizList[0].toString())
        Log.e("리사이클러뷰 2",solveQuizList[1].toString())
        Log.e("리사이클러뷰 3",solveQuizList[2].toString())
        Log.e("리사이클러뷰 4",solveQuizList[3].toString())
        Log.e("리사이클러뷰 5",solveQuizList[4].toString())
        Log.d("마이앤썰리스트 1", myAnswerList[0].toString())

    }


    inner class SolveQuizholder(view : View) : RecyclerView.ViewHolder(view){

        val tv_content = view.findViewById<TextView>(R.id.solve_quiz_content)
        val iv_yes = view.findViewById<ImageView>(R.id.img_sq_o)
        val iv_no = view.findViewById<ImageView>(R.id.img_sq_x)

        fun bind (solve_quiz: Quiz, index: Int) {
            // 질문이 없는 경우
            if (solve_quiz.content == "") {
                tv_content?.setText("응 질문없어~")
            } else {
                tv_content?.setText(solve_quiz.content)
            }

            fun setYes(){
                iv_yes.setImageResource(R.drawable.img_o_act)
                iv_no.setImageResource(R.drawable.img_x_not)
                myAnswerList[index] = 1
            }

            fun setNo(){
                iv_yes.setImageResource(R.drawable.img_o)
                iv_no.setImageResource(R.drawable.img_x)
                myAnswerList[index] = 0
            }


            fun clearYesNo(){
                    iv_yes.setImageResource(R.drawable.img_o)
                    iv_no.setImageResource(R.drawable.img_x_not)
                    myAnswerList[index] = -1
            }


            // O 버튼 클릭 리스너
            iv_yes.setOnClickListener{
                //answer=1로 바꾸기(yes활성화)
                if(myAnswerList[index] < 0){
                    setYes()
                }

                else if(myAnswerList[index] == 1){
                    clearYesNo()
                }

                else{
                    setYes()
                }
            }

            // X 버튼 클릭 리스너
            iv_no.setOnClickListener{
                ///answer=0로 바꾸기(no활성화)
                if(myAnswerList[index] < 0){
                    setNo()
                }

                else if(myAnswerList[index] == 1){
                    setNo()
                }

                else{
                    clearYesNo()
                }
            }
        }
    }
}
