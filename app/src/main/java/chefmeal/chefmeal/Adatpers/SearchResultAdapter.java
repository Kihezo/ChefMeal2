package chefmeal.chefmeal.Adatpers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import chefmeal.chefmeal.Fragments.SearchResultItemActivity;
import chefmeal.chefmeal.R;


public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultHolder> {

    private ArrayList<SearchResultItemActivity> mSearchResultList;
    private OnClickItemListener mListener;

    // Deux méthodes pour la gestion des évènements de clique
    public interface OnClickItemListener{
        void onItemClick(int position);
    }
    public void setOnClickListener(OnClickItemListener listener){
        mListener = listener;
    }

    public static class SearchResultHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;

        public SearchResultHolder(View itemView, final OnClickItemListener listener){
            super(itemView);
            mTextView = itemView.findViewById(R.id.SIR_title);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }

                }
            });
        }
    }

    public SearchResultAdapter(ArrayList<SearchResultItemActivity> SearchResultList){
        mSearchResultList = SearchResultList;
    }

    @NonNull
    @Override
    public SearchResultHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_result_item, viewGroup, false);
        SearchResultHolder evh = new SearchResultHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultHolder searchResultHolder, int position) {
        SearchResultItemActivity currentSR = mSearchResultList.get(position);

        searchResultHolder.mTextView.setText(currentSR.getmText());
    }

    @Override
    public int getItemCount() {
        return mSearchResultList.size();
    }
}
