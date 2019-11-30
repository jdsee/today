package com.mobila.project.today.activities.editorActivity;

import android.graphics.Typeface;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.editorActivity.listeners.EditorContentTextChangeListener;
import com.mobila.project.today.modelMock.NoteMock;
import com.mobila.project.today.utils.CustomTabWidthSpan;

class EditorNoteControl {

    private EditorActivity activity;
    private NoteMock note;
    private EditText editorContent;

    EditorNoteControl(EditorActivity activity, NoteMock note){
        this.activity=activity;
        this.editorContent = activity.findViewById(R.id.editor_note);
        this.note=note;
        editorContent.addTextChangedListener(
                new EditorContentTextChangeListener(this,
                        this.editorContent, this.note));
    }



    /**
     * Method for choosing a style to be set on the selected text
     */
    void applyStyle(View view){
        switch (view.getId()) {
            //Text-Color Options
            case R.id.font_color_yellow:
                setStyle(new ForegroundColorSpan(
                        ContextCompat.getColor(activity, R.color.textcolour_yellow)));
                break;
            case R.id.font_color_orange:
                setStyle(new ForegroundColorSpan(
                        ContextCompat.getColor(activity, R.color.textcolour_orange)));
                break;
            case R.id.font_color_red:
                setStyle(new ForegroundColorSpan(
                        ContextCompat.getColor(activity, R.color.textcolour_red)));
                break;
            case R.id.font_color_purple:
                setStyle(new ForegroundColorSpan(
                        ContextCompat.getColor(activity, R.color.textcolour_purple)));
                break;
            case R.id.font_color_blue:
                setStyle(new ForegroundColorSpan(
                        ContextCompat.getColor(activity, R.color.textcolour_blue)));
                break;
            case R.id.font_color_green:
                setStyle(new ForegroundColorSpan(
                        ContextCompat.getColor(activity, R.color.textcolour_green)));
                break;

            //Highlighter Options
            case R.id.font_highlighter_yellow:
                setStyle(new BackgroundColorSpan(
                        ContextCompat.getColor(activity, R.color.highlighter_yellow)));
                break;
            case R.id.font_highlighter_green:
                setStyle(new BackgroundColorSpan(
                        ContextCompat.getColor(activity, R.color.highlighter_green)));
                break;
            case R.id.font_highlighter_blue:
                setStyle(new BackgroundColorSpan(
                        ContextCompat.getColor(activity, R.color.highlighter_blue)));
                break;
            case R.id.font_highlighter_purple:
                setStyle(new BackgroundColorSpan(
                        ContextCompat.getColor(activity, R.color.highlighter_purple)));
                break;
            case R.id.font_highlighter_red:
                setStyle(new BackgroundColorSpan(
                        ContextCompat.getColor(activity, R.color.highlighter_red)));
                break;

            //Style Options
            case R.id.font_style_bold:
                setStyle(new StyleSpan(Typeface.BOLD));
                break;
            case R.id.font_style_italic:
                setStyle(new StyleSpan(Typeface.ITALIC));
                break;
            case R.id.font_style_underlined:
                setStyle(new UnderlineSpan());
                break;
            default:
                break;
        }
        activity.closeMenus();
    }


    /**
     * Applies given Style onto the selected text
     *
     * @param parcelable The style that should be applied
     */
    private void setStyle(Parcelable parcelable) {
        int startSelection = this.editorContent.getSelectionStart();
        int endSelection = this.editorContent.getSelectionEnd();
        this.note.getContent().setSpan(parcelable, startSelection, endSelection, 0);
        this.editorContent.setText(this.note.getContent(), TextView.BufferType.SPANNABLE);
        //moves cursor to the end of the selection
        this.editorContent.setSelection(endSelection);
    }

    /**
     * Method for inserting a tab in the note-content
     */
    void insertTab() {
        int tabWidth = 150;
        int startSelection = this.editorContent.getSelectionStart();
        int endSelection = this.editorContent.getSelectionEnd();
        String tab = "\t";
        editorContent.getText().delete(startSelection, endSelection);
        editorContent.getText().insert(startSelection, tab);
        this.note.setContent(editorContent.getText());
        this.note.getContent().setSpan(
                new CustomTabWidthSpan(
                        Float.valueOf(tabWidth).intValue()),
                startSelection, startSelection + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        this.editorContent.setText(this.note.getContent(), TextView.BufferType.SPANNABLE);
        //moves cursor to the end of the selection
        this.editorContent.setSelection(startSelection + 1);
    }
}