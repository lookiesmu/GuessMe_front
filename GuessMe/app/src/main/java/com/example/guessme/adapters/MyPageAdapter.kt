package com.example.guessme.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guessme.R
import com.example.guessme.data.Quiz
import com.example.guessme.data.Rank

class MyPageAdapter(private val rankList: ArrayList<Rank>) :
    RecyclerView.Adapter<MyPageAdapter.MyPageViewHolder>() {

    class MyPageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_idx = itemView?.findViewById<TextView>(R.id.tv_rank_index)
        val tv_nickname = itemView?.findViewById<TextView>(R.id.tv_rank_user)
        val tv_score = itemView?.findViewById<TextView>(R.id.tv_rank_score)

        fun bind (rank: Rank, index : Int) {
            tv_idx.setText(rank.index)
            tv_nickname.setText(rank.nickname)
            tv_score.setText(rank.score)
        }
    }


    // 화면을 최초 로딩하여 만들어진 View가 없는 경우, xml파일을 inflate하여 ViewHolder를 생성
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyPageAdapter.MyPageViewHolder {
        // create a new view
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_rank, parent, false)
        return MyPageViewHolder(itemView)
    }

    // 위의 onCreateViewHolder에서 만든 view와 실제 입력되는 각각의 데이터를 연결
    override fun onBindViewHolder(holder: MyPageViewHolder, position: Int) {
        holder?.bind(rankList[position], position)
        Log.d("리사이클러뷰 불러짐","성공")
    }

    //  RecyclerView로 만들어지는 item의 총 개수를 반환
    override fun getItemCount() = rankList.size
}