package com.mobila.project.today.control;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.mobila.project.today.R;
import com.mobila.project.today.utils.CustomTabWidthSpan;

public class NoteControl {

    private Context context;
    private EditText editorContent;

    public NoteControl(Context context, EditText editorContent) {
        this.context = context;
        this.editorContent = editorContent;
    }

    /**
     * Method for choosing a style to be set on the selected text
     */
    public void applyStyle(View view) {
        switch (view.getId()) {
            //Text-Color Options
            case R.id.font_color_yellow:
                setForegroundColorSpan(R.color.textcolour_yellow);
                break;
            case R.id.font_color_orange:
                setForegroundColorSpan(R.color.textcolour_orange);
                break;
            case R.id.font_color_red:
                setForegroundColorSpan(R.color.textcolour_red);
                break;
            case R.id.font_color_purple:
                setForegroundColorSpan(R.color.textcolour_purple);
                break;
            case R.id.font_color_blue:
                setForegroundColorSpan(R.color.textcolour_blue);
                break;
            case R.id.font_color_green:
                setForegroundColorSpan(R.color.textcolour_green);
                break;
            case R.id.font_color_black:
                setForegroundColorSpan(R.color.icon_black);
                break;

            //Highlighter Options
            case R.id.font_highlighter_yellow:
                setBackgroundColorSpan(R.color.highlighter_yellow);
                break;
            case R.id.font_highlighter_green:
                setBackgroundColorSpan(R.color.highlighter_green);
                break;
            case R.id.font_highlighter_blue:
                setBackgroundColorSpan( R.color.highlighter_blue);
                break;
            case R.id.font_highlighter_purple:
                setBackgroundColorSpan(R.color.highlighter_purple);
                break;
            case R.id.font_highlighter_red:
                setBackgroundColorSpan(R.color.highlighter_red);
                break;
            case R.id.font_highlighter_remove:
                setBackgroundColorSpan(R.color.white);
                break;

            //Style Options
            case R.id.font_style_bold:
                setSpan(new StyleSpan(Typeface.BOLD), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                break;
            case R.id.font_style_italic:
                setSpan(new StyleSpan(Typeface.ITALIC), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                break;
            case R.id.font_style_underlined:
                setSpan(new UnderlineSpan(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                break;
            default:
                break;
        }
    }

    private void setForegroundColorSpan(int id){
        setSpan(new ForegroundColorSpan(
                        ContextCompat.getColor(context, id)),
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    }

    private void setBackgroundColorSpan(int id){
        setSpan(new BackgroundColorSpan(
                        ContextCompat.getColor(context, id)),
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    }


    /**
     * Applies given Style onto the selected text
     *
     * @param parcelable The style that should be applied
     */
    private void setSpan(Parcelable parcelable, int type) {
        int startSelection = this.editorContent.getSelectionStart();
        int endSelection = this.editorContent.getSelectionEnd();
        this.editorContent.getText().setSpan(
                parcelable, startSelection, endSelection, type);
        this.editorContent.setSelection(endSelection);
    }

    /**
     * Method for inserting a tab in the note-content
     */
    public void insertTab() {
        int tabWidth = 150;
        int startSelection = this.editorContent.getSelectionStart();
        int endSelection = this.editorContent.getSelectionEnd();
        String tab = "\t";
        editorContent.getText().delete(startSelection, endSelection);
        editorContent.getText().insert(startSelection, tab);
        this.editorContent.getText().setSpan(
                new CustomTabWidthSpan(
                        Float.valueOf(tabWidth).intValue()),
                startSelection, startSelection + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //moves cursor to the end of the selection
        this.editorContent.setSelection(startSelection + 1);
    }
}