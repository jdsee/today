package com.mobila.project.today.activities.editorActivity;

import android.graphics.Typeface;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.mobila.project.today.R;
import com.mobila.project.today.activities.editorActivity.listeners.EditorContentTextChangeListener;
import com.mobila.project.today.modelMock.NoteMock;
import com.mobila.project.today.utils.CustomTabWidthSpan;

class EditorNoteControl {
    private AppCompatActivity context;
    private NoteMock note;
    private EditText editorContent;

    EditorNoteControl(AppCompatActivity context, NoteMock note){
        this.context=context;
        this.editorContent = context.findViewById(R.id.editor_note);
        this.note=note;
        editorContent.addTextChangedListener(
                new EditorContentTextChangeListener(this,
                        this.editorContent, this.note));
    }

    /**
     * Method for choosing a style to be set on the selected text
     * @param item the style that was selected
     * @return if an operation was successful
     */
    boolean choseStyle(MenuItem item){
        switch (item.getItemId()) {
            //Text-Color Options
            case R.id.colour_yellow:
                setStyle(new ForegroundColorSpan(
                        ContextCompat.getColor(context, R.color.textcolour_yellow)));
                return true;
            case R.id.colour_orange:
                setStyle(new ForegroundColorSpan(
                        ContextCompat.getColor(context, R.color.textcolour_orange)));
                return true;
            case R.id.colour_red:
                setStyle(new ForegroundColorSpan(
                        ContextCompat.getColor(context, R.color.textcolour_red)));
                return true;
            case R.id.colour_purple:
                setStyle(new ForegroundColorSpan(
                        ContextCompat.getColor(context, R.color.textcolour_purple)));
                return true;
            case R.id.colour_blue:
                setStyle(new ForegroundColorSpan(
                        ContextCompat.getColor(context, R.color.textcolour_blue)));
                return true;
            case R.id.colour_green:
                setStyle(new ForegroundColorSpan(
                        ContextCompat.getColor(context, R.color.textcolour_green)));
                return true;

            //Highlighter Options
            case R.id.highlighter_yellow:
                setStyle(new BackgroundColorSpan(
                        ContextCompat.getColor(context, R.color.highlighter_yellow)));
                return true;
            case R.id.highlighter_green:
                setStyle(new BackgroundColorSpan(
                        ContextCompat.getColor(context, R.color.highlighter_green)));
                return true;
            case R.id.highlighter_blue:
                setStyle(new BackgroundColorSpan(
                        ContextCompat.getColor(context, R.color.highlighter_blue)));
                return true;
            case R.id.highlighter_purple:
                setStyle(new BackgroundColorSpan(
                        ContextCompat.getColor(context, R.color.highlighter_purple)));
                return true;
            case R.id.highlighter_red:
                setStyle(new BackgroundColorSpan(
                        ContextCompat.getColor(context, R.color.highlighter_red)));
                return true;

            //Style Options
            case R.id.style_bold:
                setStyle(new StyleSpan(Typeface.BOLD));
                return true;
            case R.id.style_italic:
                setStyle(new StyleSpan(Typeface.ITALIC));
                return true;
            case R.id.style_underlined:
                setStyle(new UnderlineSpan());
                return true;
            default:
                return false;
        }
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
