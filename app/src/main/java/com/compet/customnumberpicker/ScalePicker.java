package com.compet.customnumberpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.Arrays;

public class ScalePicker extends LinearLayout {

    public static final boolean IS_CYCLIC_DEFAULT = true;
    public static final boolean IS_CURVED_DEFAULT = false;
    public static final boolean CAN_BE_ON_PAST_DEFAULT = false;
    public static final int DELAY_BEFORE_CHECK_PAST = 200;
    private static final int VISIBLE_ITEM_COUNT_DEFAULT = 7;

    private WheelNumberPicker decimalPicker;
    private WheelNumberPicker numberPicker;

    private Listener listener;

    private int textColor;
    private int selectedTextColor;
    private int textSize;
    private boolean isCyclic;
    private boolean isCurved;
    private int visibleItemCount;

    private boolean canBeOnPast;

    public ScalePicker(Context context) {
        this(context, null);
    }

    public ScalePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScalePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        inflate(context, R.layout.single_day_picker, this);

        decimalPicker = (WheelNumberPicker) findViewById(R.id.np_decimal);
        numberPicker = (WheelNumberPicker) findViewById(R.id.np_point);

        decimalPicker.setOnPickerClickListener(new WheelNumberPicker.OnPickerClickListener() {
            @Override
            public void onClick() {

                clickListener();

            }
        });

        decimalPicker.setOnNumberSelectedListener(new WheelNumberPicker.OnNumberSelectedListener() {
            @Override
            public void onNumberSelected(WheelNumberPicker picker, int position, int number) {
                updateListener();
//                checkInPast(picker);
            }

            @Override
            public void onNumberCurrentScrolled(WheelNumberPicker picker, int position, int number) {

            }

            @Override
            public void onNumberScrolledNewNumber(WheelNumberPicker picker) {

            }
        });

        numberPicker.setOnNumberSelectedListener(new WheelNumberPicker.OnNumberSelectedListener() {
            @Override
            public void onNumberSelected(WheelNumberPicker picker, int position, int number) {
                updateListener();
            }

            @Override
            public void onNumberCurrentScrolled(WheelNumberPicker picker, int position, int number) {

            }

            @Override
            public void onNumberScrolledNewNumber(WheelNumberPicker picker) {

            }
        });

        updatePicker();
    }

    public void setCurved(boolean curved) {
        isCurved = curved;
        updatePicker();
    }

    public void setCyclic(boolean cyclic) {
        isCyclic = cyclic;
        updatePicker();
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        updatePicker();
    }

    public void setSelectedTextColor(int selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
        updatePicker();
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        updatePicker();
    }


    public void setVisibleItemCount(int visibleItemCount) {
        this.visibleItemCount = visibleItemCount;
        updatePicker();
    }

    private void updatePicker() {
        if (decimalPicker != null && numberPicker != null) {
            for (WheelPicker wheelPicker : Arrays.asList(decimalPicker, numberPicker)) {
                wheelPicker.setItemTextColor(textColor);
                wheelPicker.setSelectedItemTextColor(selectedTextColor);
                wheelPicker.setItemTextSize(textSize);
                wheelPicker.setCyclic(isCyclic);
                wheelPicker.setCurved(isCurved);
                wheelPicker.setVisibleItemCount(visibleItemCount);
            }
        }
    }

//    private void checkInPast(final WheelPicker picker) {
//        picker.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (!canBeOnPast && isInPast(getDate())) {
//                    decimalPicker.scrollTo(decimalPicker.getDefaultItemPosition());
//                    numberPicker.scrollTo(numberPicker.getDefaultItemPosition());
//                }
//            }
//        }, DELAY_BEFORE_CHECK_PAST);
//    }
//
//    private boolean isInPast(Date date) {
//        final Calendar todayCalendar = Calendar.getInstance();
//        todayCalendar.set(Calendar.MILLISECOND, 0);
//        todayCalendar.set(Calendar.SECOND, 0);
//
//        final Calendar dateCalendar = Calendar.getInstance();
//        dateCalendar.set(Calendar.MILLISECOND, 0);
//        dateCalendar.set(Calendar.SECOND, 0);
//
//        dateCalendar.setTime(date);
//        return dateCalendar.before(todayCalendar);
//    }
//
    public void setListener(Listener listener) {
        this.listener = listener;
    }

//    public Date getDate() {
//        final int hour = hoursPicker.getCurrentHour();
//        final int minute = minutesPicker.getCurrentNumber();
//
//        final Calendar calendar = Calendar.getInstance();
//        final Date dayDate = decimalPicker.getCurrentDate();
//        calendar.setTime(dayDate);
//        calendar.set(Calendar.HOUR_OF_DAY, hour);
//        calendar.set(Calendar.MINUTE, minute);
//
//        final Date time = calendar.getTime();
//        return time;
//    }

//    public void selectDate(Calendar calendar) {
//        if (calendar == null) {
//            return;
//        }
//        Date date = calendar.getTime();
//        int indexOfDay = decimalPicker.findIndexOfDate(date);
//        if (indexOfDay != 0) {
//            decimalPicker.setSelectedItemPosition(indexOfDay);
//        }
//        int indexOfHour = hoursPicker.findIndexOfDate(date);
//        if (indexOfHour != 0) {
//            hoursPicker.setSelectedItemPosition(indexOfHour);
//        }
//        int indexOfMin = minutesPicker.findIndexOfDate(date);
//        if (indexOfMin != 0) {
//            minutesPicker.setSelectedItemPosition(indexOfMin);
//        }
//    }

    private void updateListener() {
        final String displayed = String.valueOf(decimalPicker.getCurrentNumber() + "." + numberPicker.getCurrentNumber());

        if (listener != null) {
            listener.onNumberChanged(displayed, 1);
        }
    }

    private void clickListener() {
        if(listener != null) {
            listener.onClickChanged();
        }
    }

    public void setCanBeOnPast(boolean canBeOnPast) {
        this.canBeOnPast = canBeOnPast;
    }

    public boolean canBeOnPast() {
        return canBeOnPast;
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SingleDateAndTimePicker);

        textColor = a.getColor(R.styleable.SingleDateAndTimePicker_picker_textColor,
                getResources().getColor(R.color.picker_default_text_color));
        selectedTextColor = a.getColor(R.styleable.SingleDateAndTimePicker_picker_selectedTextColor,
                getResources().getColor(R.color.picker_default_selected_text_color));
        textSize = a.getDimensionPixelSize(R.styleable.SingleDateAndTimePicker_picker_textSize,
                getResources().getDimensionPixelSize(R.dimen.WheelItemTextSize));
        isCurved = a.getBoolean(R.styleable.SingleDateAndTimePicker_picker_curved, IS_CURVED_DEFAULT);
        isCyclic = a.getBoolean(R.styleable.SingleDateAndTimePicker_picker_cyclic, IS_CYCLIC_DEFAULT);
        canBeOnPast = a.getBoolean(R.styleable.SingleDateAndTimePicker_picker_canBeOnPast, CAN_BE_ON_PAST_DEFAULT);
        visibleItemCount = a.getInt(R.styleable.SingleDateAndTimePicker_picker_visibleItemCount, VISIBLE_ITEM_COUNT_DEFAULT);

        a.recycle();
    }

    public interface Listener {
        void onNumberChanged(String displayed, int number);

        void onClickChanged();
    }
}
