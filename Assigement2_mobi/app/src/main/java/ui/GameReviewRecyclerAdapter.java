package ui;

import android.content.Context;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.assigement2_mobi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.GameReview;

public class GameReviewRecyclerAdapter extends RecyclerView.Adapter<GameReviewRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<GameReview> gameReviewsList;

    public GameReviewRecyclerAdapter(Context context, List<GameReview> gameReviewsList) {
        this.context = context;
        this.gameReviewsList = gameReviewsList;
    }

    @NonNull
    @Override
    public GameReviewRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.reviews_row, viewGroup, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull GameReviewRecyclerAdapter.ViewHolder viewHolder, int position) {

        GameReview gameReview = gameReviewsList.get(position);
        String imageUrl;

        viewHolder.Gametitle.setText(gameReview.getTitle());
        viewHolder.Review.setText(gameReview.getReview());
        viewHolder.name.setText(gameReview.getUserName());
        imageUrl = gameReview.getImageUrl();

        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(gameReview
                .getTimeAdded()
                .getSeconds() * 1000);
        viewHolder.dateAdded.setText(timeAgo);


        /*
         Use Picasso library to download and show image
         */
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.image_three)
                .fit()
                .into(viewHolder.image);

    }

    @Override
    public int getItemCount() {
        return gameReviewsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView
                Gametitle,
                Review,
                dateAdded,
                name;
        public ImageView image;
        public ImageButton shareButton;
        String userId;
        String username;


        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            Gametitle = itemView.findViewById(R.id.game_title_list);
            Review = itemView.findViewById(R.id.reviews_list);
            dateAdded = itemView.findViewById(R.id.review_timestamp_list);
            image = itemView.findViewById(R.id.reviews_image_list);
            name = itemView.findViewById(R.id.journal_row_username);

            shareButton = itemView.findViewById(R.id.journal_row_share_button);
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //context.startActivity();
                }
            });

        }
    }
}
