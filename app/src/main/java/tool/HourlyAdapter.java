package tool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suixinweather.R;

import java.util.List;

import data.Hourly;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder> {

    List<Hourly> hourlyList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView degree;
        ImageView icon;
        TextView text;
        TextView date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            degree = (TextView) itemView.findViewById(R.id.hourly_degree);
            icon = (ImageView) itemView.findViewById(R.id.hourly_icon);
            text = (TextView) itemView.findViewById(R.id.hourly_text);
            date = (TextView) itemView.findViewById(R.id.hourly_date);
        }
    }

    public HourlyAdapter(List<Hourly> hourlyList) {
        this.hourlyList = hourlyList;
    }

    public void set(List<Hourly> hourlyList) {
        this.hourlyList = hourlyList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hourly hourly = hourlyList.get(position);
        holder.degree.setText(hourly.temp);
//        holder.icon.setImageResource(hourly.icon);
        holder.text.setText(hourly.text);
        holder.date.setText(hourly.fxTime);
        switch (hourly.icon){
            case 100:
                holder.icon.setImageResource(R.drawable._100);
                break;
            case 101:
                holder.icon.setImageResource(R.drawable._101);
                break;
            case 102:
                holder.icon.setImageResource(R.drawable._102);
                break;
            case 103:
                holder.icon.setImageResource(R.drawable._103);
                break;
            case 104:
                holder.icon.setImageResource(R.drawable._104);
                break;
            case 150:
                holder.icon.setImageResource(R.drawable._150);
                break;
            case 151:
                holder.icon.setImageResource(R.drawable._151);
                break;
            case 152:
                holder.icon.setImageResource(R.drawable._152);
                break;
            case 153:
                holder.icon.setImageResource(R.drawable._153);
                break;
            case 300:
                holder.icon.setImageResource(R.drawable._300);
                break;
            case 301:
                holder.icon.setImageResource(R.drawable._301);
                break;
            case 302:
                holder.icon.setImageResource(R.drawable._302);
                break;
            case 303:
                holder.icon.setImageResource(R.drawable._303);
                break;
            case 304:
                holder.icon.setImageResource(R.drawable._304);
                break;
            case 305:
                holder.icon.setImageResource(R.drawable._305);
                break;
            case 306:
                holder.icon.setImageResource(R.drawable._306);
                break;
            case 307:
                holder.icon.setImageResource(R.drawable._307);
                break;
            case 308:
                holder.icon.setImageResource(R.drawable._308);
                break;
            case 309:
                holder.icon.setImageResource(R.drawable._309);
                break;
            case 310:
                holder.icon.setImageResource(R.drawable._310);
                break;
            case 311:
                holder.icon.setImageResource(R.drawable._311);
                break;
            case 312:
                holder.icon.setImageResource(R.drawable._312);
                break;
            case 313:
                holder.icon.setImageResource(R.drawable._313);
                break;
            case 314:
                holder.icon.setImageResource(R.drawable._314);
                break;
            case 315:
                holder.icon.setImageResource(R.drawable._315);
                break;
            case 316:
                holder.icon.setImageResource(R.drawable._316);
                break;
            case 317:
                holder.icon.setImageResource(R.drawable._317);
                break;
            case 318:
                holder.icon.setImageResource(R.drawable._318);
                break;
            case 350:
                holder.icon.setImageResource(R.drawable._350);
                break;
            case 351:
                holder.icon.setImageResource(R.drawable._351);
                break;
            case 400:
                holder.icon.setImageResource(R.drawable._400);
                break;
            case 401:
                holder.icon.setImageResource(R.drawable._401);
                break;
            case 402:
                holder.icon.setImageResource(R.drawable._402);
                break;
            case 403:
                holder.icon.setImageResource(R.drawable._403);
                break;
            case 404:
                holder.icon.setImageResource(R.drawable._404);
                break;
            case 405:
                holder.icon.setImageResource(R.drawable._405);
                break;
            case 406:
                holder.icon.setImageResource(R.drawable._406);
                break;
            case 407:
                holder.icon.setImageResource(R.drawable._407);
                break;
            case 408:
                holder.icon.setImageResource(R.drawable._408);
                break;
            case 409:
                holder.icon.setImageResource(R.drawable._409);
                break;
            case 410:
                holder.icon.setImageResource(R.drawable._410);
                break;
            case 456:
                holder.icon.setImageResource(R.drawable._456);
                break;
            case 457:
                holder.icon.setImageResource(R.drawable._457);
                break;
            case 499:
                holder.icon.setImageResource(R.drawable._499);
                break;
            case 500:
                holder.icon.setImageResource(R.drawable._500);
                break;
            case 501:
                holder.icon.setImageResource(R.drawable._501);
                break;
            case 502:
                holder.icon.setImageResource(R.drawable._502);
                break;
            case 503:
                holder.icon.setImageResource(R.drawable._503);
                break;
            case 504:
                holder.icon.setImageResource(R.drawable._504);
                break;
            case 507:
                holder.icon.setImageResource(R.drawable._507);
                break;
            case 508:
                holder.icon.setImageResource(R.drawable._508);
                break;
            case 509:
                holder.icon.setImageResource(R.drawable._509);
                break;
            case 510:
                holder.icon.setImageResource(R.drawable._510);
                break;
            case 514:
                holder.icon.setImageResource(R.drawable._514);
                break;
            case 515:
                holder.icon.setImageResource(R.drawable._515);
                break;
            case 999:
                holder.icon.setImageResource(R.drawable._999);
                break;
            case 1001:
                holder.icon.setImageResource(R.drawable._1001);
                break;
            case 1002:
                holder.icon.setImageResource(R.drawable._1002);
                break;
            case 1003:
                holder.icon.setImageResource(R.drawable._1003);
                break;
            case 1004:
                holder.icon.setImageResource(R.drawable._1004);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (hourlyList == null)
            return 0;
        else
            return hourlyList.size();
    }
}
