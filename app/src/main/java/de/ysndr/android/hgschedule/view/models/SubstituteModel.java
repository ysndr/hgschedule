package de.ysndr.android.hgschedule.view.models;

import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.ysndr.android.hgschedule.R;
import de.ysndr.android.hgschedule.state.models.Substitute;
import de.ysndr.android.hgschedule.view.views.SubstituteView;

/**
 * Created by yannik on 5/12/17.
 */

@EpoxyModelClass(layout = R.layout.model_substitute)
public abstract class SubstituteModel extends EpoxyModel<SubstituteView> {

    @EpoxyAttribute
    Substitute substitute;

    @BindView(R.id.text_title_card_schedule_item)
    TextView title;
    @BindView(R.id.text_description_card_schedule_item)
    TextView description;
    @BindView(R.id.text_teacher_absent_card_schedule_item)
    TextView absent;
    @BindView(R.id.text_teacher_new_card_schedule_item)
    TextView subst;
    @BindView(R.id.text_lessons_card_schedule_item)
    TextView lessons;
    @BindView(R.id.text_classes_card_schedule_item)
    TextView classes;

    @BindView(R.id.text_room_card_schedule_item)
    TextView room;
    Unbinder unbinder;

    @Override
    public void bind(SubstituteView view) {
        super.bind(view);
        unbinder = ButterKnife.bind(this, view);

        absent.setText(substitute.absent());
        subst.setText(substitute.substitute());
        classes.setText(substitute.classes());
        lessons.setText(substitute.hour());
        description.setText(substitute.description());
        room.setText(substitute.room());
        title.setText(substitute.absent());
        // TODO maybe change to a visible/invisible filtering
    }

    @Override
    public void unbind(SubstituteView view) {
        super.unbind(view);
        unbinder.unbind();
    }
}