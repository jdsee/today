package com.mobila.project.today.control;

import android.graphics.Typeface;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.mobila.project.today.R;
import com.mobila.project.today.utils.CustomTabWidthSpan;

public class NoteControl {

    private AppCompatActivity activity;
    private EditText editorContent;

    public NoteControl(AppCompatActivity activity){
        this.activity=activity;
        this.editorContent = activity.findViewById(R.id.editor_note);
    }

    /**
     * Method for choosing a style to be set on the selected text
     */
    public void applyStyle(View view){
        switch (view.getId()) {
            //Text-Color Options
            case R.id.font_color_yellow:
                setSpan(new ForegroundColorSpan(
                        ContextCompat.getColor(activity, R.color.textcolour_yellow)),
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                break;
            case R.id.font_color_orange:
                setSpan(new ForegroundColorSpan(
                        ContextCompat.getColor(activity, R.color.textcolour_orange)),
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                break;
            case R.id.font_color_red:
                setSpan(new ForegroundColorSpan(
                        ContextCompat.getColor(activity, R.color.textcolour_red)),
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                break;
            case R.id.font_color_purple:
                setSpan(new ForegroundColorSpan(
                        ContextCompat.getColor(activity, R.color.textcolour_purple)),
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                break;
            case R.id.font_color_blue:
                setSpan(new ForegroundColorSpan(
                        ContextCompat.getColor(activity, R.color.textcolour_blue)),
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                break;
            case R.id.font_color_green:
                setSpan(new ForegroundColorSpan(
                        ContextCompat.getColor(activity, R.color.textcolour_green)),
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                break;
            case R.id.font_color_black:
                setSpan(new ForegroundColorSpan(
                        ContextCompat.getColor(activity, R.color.icon_black)),
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                break;

            //Highlighter Options
            case R.id.font_highlighter_yellow:
                setSpan(new BackgroundColorSpan(
                        ContextCompat.getColor(activity, R.color.highlighter_yellow)),
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                break;
            case R.id.font_highlighter_green:
                setSpan(new BackgroundColorSpan(
                        ContextCompat.getColor(activity, R.color.highlighter_green)),
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                break;
            case R.id.font_highlighter_blue:
                setSpan(new BackgroundColorSpan(
                        ContextCompat.getColor(activity, R.color.highlighter_blue)),
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                break;
            case R.id.font_highlighter_purple:
                setSpan(new BackgroundColorSpan(
                        ContextCompat.getColor(activity, R.color.highlighter_purple)),
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                break;
            case R.id.font_highlighter_red:
                setSpan(new BackgroundColorSpan(
                        ContextCompat.getColor(activity, R.color.highlighter_red)),
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                break;
            case R.id.font_highlighter_remove:
                setSpan(new BackgroundColorSpan(
                        ContextCompat.getColor(activity, R.color.white)),
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);
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