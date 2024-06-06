package tool;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.suixinweather.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.CardWeather;
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    public List<CardWeather> cardWeathers;
    public Map<String, Boolean> mSelectedItem;
    public boolean isVisibility;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView place;
        TextView airQue;
        TextView tempMax;
        TextView tempMin;
        TextView temp;
        CheckBox checkBox;
        boolean check;
        public ViewHolder(@NonNull View view) {
            super(view);
            place = view.findViewById(R.id.card_loc);
            airQue = view.findViewById(R.id.card_air);
            temp = view.findViewById(R.id.card_temp);
            tempMax = view.findViewById(R.id.card_max);
            tempMin = view.findViewById(R.id.card_min);
            checkBox = view.findViewById(R.id.checkBox);
            check = false;
        }
    }

    public CardAdapter(List<CardWeather> cardWeathers) {
        this.cardWeathers = cardWeathers;
        mSelectedItem = new HashMap<>();
        isVisibility = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardWeather cardWeather = cardWeathers.get(position);
        holder.place.setText(cardWeather.placeName);
        holder.airQue.setText(cardWeather.airQua);
        holder.tempMax.setText(cardWeather.tempMax);
        holder.tempMin.setText(cardWeather.tempMin);
        holder.temp.setText(cardWeather.temp);
        if (isVisibility){
            holder.checkBox.setVisibility(View.VISIBLE);
        }else {
            holder.checkBox.setVisibility(View.GONE);
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if (isCheck){
                    holder.check = true;
                    mSelectedItem.put(cardWeather.placeName,true);
                    Log.d("DeleteCity1", "isCheck: " + cardWeather.placeName + "   mSelectedItem.size()" +mSelectedItem.size());
                }else {
                    holder.check = false;
                    mSelectedItem.put(cardWeather.placeName, false);
                    Log.d("DeleteCity2", " ! isCheck  !: " + cardWeather.placeName);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.check){
                    holder.check = true;
                    holder.checkBox.setChecked(true);
                    mSelectedItem.put(cardWeather.placeName,true);
                    Log.d("DeleteCity1", "isCheck: " + cardWeather.placeName + "   mSelectedItem.size()" +mSelectedItem.size());
                }else {
                    holder.check = false;
                    holder.checkBox.setChecked(false);
                    mSelectedItem.put(cardWeather.placeName, false);
                    Log.d("DeleteCity2", " ! isCheck  !: " + cardWeather.placeName);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
       return cardWeathers.size();
    }

    public List<String> getSelectedItems(){
        List<String> cities = new ArrayList<>();
        List<String> cityMap = new ArrayList<>(mSelectedItem.keySet());
        for(String key : cityMap){
            Log.d("DeleteCity", "getSelectedItems: city" + key);
            boolean isSelect = mSelectedItem.get(key);
            Log.d("DeleteCity", "getSelectedItems: value" + isSelect);
            if (isSelect){
                cities.add(key);
            }
        }
        Log.d("DeleteCity", "integers.size(): " + cities.size());
        return cities;
    }

    public void setCardWeathers(List<CardWeather> cardWeathers) {
        this.cardWeathers = cardWeathers;
    }
}
