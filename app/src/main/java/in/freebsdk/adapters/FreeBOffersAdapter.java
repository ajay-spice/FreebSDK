package in.freebsdk.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.squareup.picasso.Picasso;

import java.util.List;

import in.freeb.sdk.R;
import in.freeb.sdk.model_gson.FetchOffer;
import in.freeb.sdk.utils.FreeBConstants;
import in.freeb.sdk.utils.FreeBSDKLogger;

/**
 *
 * Adapter class extends with BaseAdapter
 */
public class FreeBOffersAdapter extends BaseAdapter {
    /**
     * Declare Used Variables
     */
    Context context;
    private AQuery aQuery;
    private List<FetchOffer> mList;
    private int lastPosition = -1;

    /**
     * @param context Context to get the resources for adapter
     * @param list    Array of Offers Data
     */
    public FreeBOffersAdapter(Context context, List<FetchOffer> list) {

        this.context = context;
        this.mList = list;
        aQuery = new AQuery(context);

    }

    /**
     * What is the size of Passed Arraylist Size
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /**
     *
     * Depends upon data size called for each row , Create each ListView row
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder = null;

        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            view = inflater.inflate(R.layout.freeb_show_offers, parent, false);
            viewHolder.vendorImage = (ImageView) view
                    .findViewById(R.id.iv_app_icon);
            viewHolder.vendorName = (TextView) view
                    .findViewById(R.id.tv_app_name);
            viewHolder.appCategory = (TextView) view
                    .findViewById(R.id.tv_app_category);
            viewHolder.appDescription = (TextView) view
                    .findViewById(R.id.tv_app_description);
            viewHolder.app_offers_relative_layout = (RelativeLayout) view
                    .findViewById(R.id.app_offers_relative_layout);
            viewHolder.layout_back = (RelativeLayout) view
                    .findViewById(R.id.layout_back);

            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

       /* AQuery imgAquery = aQuery.recycle(view);
        imgAquery.id(viewHolder.vendorImage).image(
                mList.get(position).getIconUrl(), true, true, 0,
                R.drawable.ic_launcher, null, AQuery.FADE_IN);*/

        Picasso.with(context)
                .load(mList.get(position).getIconUrl())
                .placeholder(R.drawable.download_icon) // optional
                .error(R.drawable.no_image)         // optional
                .into(viewHolder.vendorImage);

        viewHolder.vendorName.setText(Html.fromHtml(mList.get(position)
                .getOfferName()));
        viewHolder.appCategory.setText(Html.fromHtml(mList.get(position)
                .getOfferCategory()));
        viewHolder.appDescription.setText(Html.fromHtml(mList.get(position)
                .getShortDescription()));

        try {

            String colorArray[] = mList.get(position).getRgbCode().split("\\|");
            viewHolder.vendorName.setTextColor(Color.parseColor(colorArray[0]));
            viewHolder.appCategory
                    .setTextColor(Color.parseColor(colorArray[1]));
            viewHolder.appDescription.setTextColor(Color
                    .parseColor(colorArray[2]));
            viewHolder.app_offers_relative_layout.setBackgroundColor(Color
                    .parseColor(colorArray[3]));
        } catch (Exception e) {
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }

//		try {
//			if (view != null) {
//				Animation animation = AnimationUtils.loadAnimation(context,
//						(position > lastPosition) ? R.anim.up_from_bottom
//								: R.anim.down_from_up);
//				view.startAnimation(animation);
//				lastPosition = position;
//			}
//		} catch (NotFoundException e) {
//			Crashlytics.logException(e);
//		}

        return view;
    }

    /**
     * Create a holder Class to contain inflated xml file elements
     */
    public class ViewHolder {

        private ImageView vendorImage;
        private TextView vendorName, rupees, appCategory;
        private TextView appDescription;
        private ImageView bannerImage;
        private RelativeLayout app_offers_relative_layout;
        private RelativeLayout layout_back;
    }
}
