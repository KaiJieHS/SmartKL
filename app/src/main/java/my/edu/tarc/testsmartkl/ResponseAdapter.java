package my.edu.tarc.testsmartkl;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ResponseAdapter extends ArrayAdapter<FeedbackResponses> {
    public ResponseAdapter(Activity context, int resource, List<FeedbackResponses> list) {
        super(context, resource, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FeedbackResponses feedbackres = getItem(position);

        LayoutInflater inflater  = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.response_record, parent, false);

        TextView textViewFeedbackId, textViewResponseDesc, textViewResponseDate, textViewOfficerId, textViewResponseId;

        textViewFeedbackId = rowView.findViewById(R.id.textViewFeedbackId);
        textViewResponseDesc = rowView.findViewById(R.id.textViewResponseDesc);
        textViewResponseDate = rowView.findViewById(R.id.textViewResponseDate);
        textViewOfficerId = rowView.findViewById(R.id.textViewOfficerId);
        textViewResponseId = rowView.findViewById(R.id.textViewResponseId);

        textViewFeedbackId.setText(String.format("%s : %s", "Feedback ID",feedbackres.getFeedbackID()));
        textViewResponseDesc.setText(String.format("%s : %s", "Replied",feedbackres.getResponseDesc()));
        textViewResponseDate.setText(String.format("%s : %s", "Date/Time",feedbackres.getResponseDate()));
        textViewOfficerId.setText(String.format("%s : %s", "Officer ID",feedbackres.getOfficerID()));
        textViewResponseId.setText(String.format("%s : %s", "Response ID",feedbackres.getResponseID()));
        return rowView;
    }
}
