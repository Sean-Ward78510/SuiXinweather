package tool;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.suixinweather.R;
import com.example.suixinweather.SearchWeatherActivity;

import java.util.List;

import data.SearchPlace;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    List<SearchPlace> searchPlaces;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View searchView;
        TextView place;
        TextView amd2;
        TextView amd1;
        SearchPlace searchPlace;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            searchView = itemView;
            place = (TextView) itemView.findViewById(R.id.search_place);
            amd2 = (TextView) itemView.findViewById(R.id.search_amd2);
            amd1 = (TextView) itemView.findViewById(R.id.search_amd1);
            searchPlace = new SearchPlace();
        }
    }

    public SearchAdapter(List<SearchPlace> searchPlaces, Context context) {
        this.searchPlaces = searchPlaces;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SearchWeatherActivity.class);
                intent.putExtra("name",viewHolder.searchPlace.placeName);
                intent.putExtra("id",viewHolder.searchPlace.placeId);
                intent.putExtra("adm1",viewHolder.searchPlace.adm1);
                intent.putExtra("adm2",viewHolder.searchPlace.amd2);
                context.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchPlace searchPlace = searchPlaces.get(position);
        holder.place.setText(searchPlace.placeName);
        holder.amd2.setText(searchPlace.amd2);
        holder.amd1.setText(searchPlace.adm1);

        holder.searchPlace.placeName = searchPlace.placeName;
        holder.searchPlace.placeId = searchPlace.placeId;
    }

    @Override
    public int getItemCount() {
        return searchPlaces.size();
    }

}
