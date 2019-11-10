package com.mobila.project.today.control;//package com.example.editoractivity.control;
//
//import android.content.Context;
//import android.text.Editable;
//import android.view.View;
//import android.widget.EditText;
//
//import com.example.editoractivity.R;
//import com.example.editoractivity.utils.TextChangeListener;
//
//import java.util.Objects;
//
//class EditorControlImpl implements EditorControl {
//    private static EditorControlImpl singleton = null;
//    private EditText headline;
//
//    private EditorControlImpl() {
//    }
//
//    public static EditorControl getInstance(){
//        if(EditorControlImpl.singleton == null) {
//            EditorControlImpl.singleton = new EditorControlImpl();
//        }
//        return EditorControlImpl.singleton;
//    }
//
//    @Override
//    public void onHeadlineChanged(Context context, View view) {
//        final EditText headline = view.findViewById(R.id.editor_headline);
//        headline.addTextChangedListener(new TextChangeListener<EditText>(headline) {
//            @Override
//            public void onTextChanged(EditText target, Editable s) {
//                String content = headline.getText().toString();
//                Objects.requireNonNull(view.getSupportActionBar()).setTitle(content);
//                if (content.equals(""))
//                    Objects.requireNonNull(getSupportActionBar()).setTitle(improvisedEvent);
//            }
//        });
//    }
//
//    @Override
//    public void onKeyboardVisibilityChanged(Context context) {
//
//    }
//}
