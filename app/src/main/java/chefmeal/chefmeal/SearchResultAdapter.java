package chefmeal.chefmeal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultHolder> {

    private ArrayList<SearchResultItemActivity> mSearchResultList;

    public static class SearchResultHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;

        public SearchResultHolder(View itemView){
            super(itemView);
            mTextView = itemView.findViewById(R.id.SIR_title);
        }
    }

    public SearchResultAdapter(ArrayList<SearchResultItemActivity> SearchResultList){
        mSearchResultList = SearchResultList;
    }

    @NonNull
    @Override
    public SearchResultHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_result_item, viewGroup, false);
        SearchResultHolder evh = new SearchResultHolder(v);
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
