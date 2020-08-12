package com.example.guessme.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.guessme.R
import com.example.guessme.data.Quiz
import kotlinx.android.synthetic.main.list_item_create_quiz.view.*

class CreateQuizAdapter(private val quizList: ArrayList<Quiz>) :
    RecyclerView.Adapter<CreateQuizAdapter.CreateQuizViewHolder>() {

    class CreateQuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val qContent = itemView?.findViewById<TextView>(R.id.tv_content_cq)
        val iv_yes = itemView?.findViewById<ImageView>(R.id.img_cq_o)
        val iv_no = itemView?.findViewById<ImageView>(R.id.img_cq_x)

        fun bind (quiz: Quiz, index : Int) {
            // 질문이 없는 경우
            if (quiz.content != "") {
                qContent?.setText("응 질문없어~")
            } else {
                qContent?.setText(quiz.content)
            }

            // O 버튼 클릭 리스너
            iv_yes.setOnClickListener{
                //answer=1로 바꾸기(yes활성화)
                if(quiz.answer == 0){
                    iv_yes.setImageResource(R.drawable.img_o_act)
                    iv_no.setImageResource(R.drawable.img_x_not)
                    quiz.answer = 1
                } else{     // yes를 비활성화
                    iv_no.setImageResource(R.drawable.img_o)
                    quiz.answer = -1

                }
            }
            // X 버튼 클릭 리스너
            iv_no.setOnClickListener{
                ///answer=0로 바꾸기(no활성화)
                if(quiz.answer == 1){
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


    // 화면을 최초 로딩하여 만들어진 View가 없는 경우, xml파일을 inflate하여 ViewHolder를 생성
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): CreateQuizAdapter.CreateQuizViewHolder {
        // create a new view
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_create_quiz, parent, false)
        return CreateQuizViewHolder(itemView)
    }

    // 위의 onCreateViewHolder에서 만든 view와 실제 입력되는 각각의 데이터를 연결
    override fun onBindViewHolder(holder: CreateQuizViewHolder, position: Int) {
        holder?.bind(quizList[position], position)
        Log.e("리사이클러뷰 불러짐","성공")
    }

    //  RecyclerView로 만들어지는 item의 총 개수를 반환
    override fun getItemCount() = quizList.size
}