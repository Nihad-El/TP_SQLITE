package ma.emsi.tp_sqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ma.emsi.tp_sqlite.R;
import ma.emsi.tp_sqlite.bean.Machine;



public class MachineAdapter extends BaseAdapter {

    List<Machine> machines;
    private LayoutInflater layoutInflater;

    public MachineAdapter(Context context, List<Machine> machines) {
        this.machines = machines;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return machines.size();
    }

    @Override
    public Object getItem(int position) {
        return machines.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null)
            view = layoutInflater.inflate(R.layout.machine_item, null);

        TextView marque = (TextView) view.findViewById(R.id.marque);
        TextView reference = (TextView) view.findViewById(R.id.reference);
        TextView id = (TextView) view.findViewById(R.id.ids);

        id.setText(machines.get(position).getId() + "");
        marque.setText(machines.get(position).getMarque());
        reference.setText(machines.get(position).getRefernce());

        return view;
    }
}
