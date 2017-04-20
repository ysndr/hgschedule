package io.ysndr.android.hg_schedule.features.schedule.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.immutables.value.Value;

import butterknife.BindView;
import io.ysndr.android.hg_schedule.R;
import io.ysndr.android.hg_schedule.features.schedule.models.Substitute;


/**
 * Created by yannik on 12/19/16.
 */

@Value.Immutable
public abstract class SubstituteViewWrapper extends ViewWrapper {


    public abstract Substitute substitute();

    @Value.Auxiliary
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.absent.setText(substitute().absent());
        holder.substitute.setText(substitute().substitute());
        holder.classes.setText(substitute().classes());
        holder.lessons.setText(substitute().hour());
        holder.description.setText(substitute().description());
        holder.room.setText(substitute().room());
        holder.title.setText(substitute().absent());
    }

    @Override
    public ViewWrapper.TypeMapper typeMapper() {
        return new TypeMapper();
    }

    public static class ViewHolder extends ViewWrapper.ViewHolder {

        @BindView(R.id.text_title_card_schedule_item)
        TextView title;
        @BindView(R.id.text_description_card_schedule_item)
        TextView description;
        @BindView(R.id.text_teacher_absent_card_schedule_item)
        TextView absent;
        @BindView(R.id.text_teacher_new_card_schedule_item)
        TextView substitute;
        @BindView(R.id.text_lessons_card_schedule_item)
        TextView lessons;
        @BindView(R.id.text_classes_card_schedule_item)
        TextView classes;

        @BindView(R.id.text_room_card_schedule_item)
        TextView room;

        ViewHolder(View view) {
            super(view);
        }

    }

    public static class TypeMapper extends ViewWrapper.TypeMapper {
        @Override
        @Value.Auxiliary
        public int TYPE() {
            return 0;
        }

        @Override
        @Value.Auxiliary
        public int viewID() {
            return R.layout.card_schedule_item;
        }

        @Value.Auxiliary
        @Override
        public RecyclerView.ViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }
    }


}
