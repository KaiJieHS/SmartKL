package my.edu.tarc.testsmartkl;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FeedbackAdapter extends ArrayAdapter<Feedback> {
    public FeedbackAdapter(Activity context, int resource, List<Feedback> list) {
        super(context, resource, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Feedback feedback = getItem(position);

        LayoutInflater inflater  = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.feedback_record, parent, false);

        TextView textViewID, textViewType, textViewSubject, textViewDesc, textViewTime, textViewDate;

        textViewID = rowView.findViewById(R.id.textViewID);
        textViewType = rowView.findViewById(R.id.textViewType);
        textViewSubject = rowView.findViewById(R.id.textViewSubject);
        textViewDesc = rowView.findViewById(R.id.textViewDesc);
        textViewDate = rowView.findViewById(R.id.textViewDate);

        textViewID.setText(String.format("%s", feedback.getFeedbackID()));
        textViewType.setText(String.format("%s",feedback.getFeedbackType()));
        textViewSubject.setText(String.format("%s",feedback.getSubject()));
        textViewDesc.setText(String.format("%s",feedback.getDescription()));
        textViewDate.setText(String.format("%s",feedback.getDate()));
        return rowView;
    }
}
