package today.activities.courseContentView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import today.R;
import today.model.dataProviding.SampleDataProvider;
import today.activities.adapters.SectionAdapter;
import today.activities.adapters.TaskAdapter;
import today.model.Course;
import today.model.Section;
import today.model.Task;

import java.util.List;

public class CourseContentActivity extends AppCompatActivity {
    private Course course;
    private List<Task> tasks;
    private List<Section> sections;
    private TaskAdapter taskAdapter;
    private SectionAdapter sectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_content);

        this.course = this.getIntent().getParcelableExtra(Course.INTENT_EXTRA_CODE);

        ActionBar ab = this.getSupportActionBar();
        ab.setTitle(this.course.getTitle());

        this.tasks = SampleDataProvider.getExampleTasks();
        //TODO this.tasks = this.course.getTasks();
        this.initTaskView();

        this.sections = SampleDataProvider.getExampleSections();
        //TODO this.sections = this.course.getSections();
        this.initSectionView();

    }

    private void initTaskView() {
        RecyclerView recyclerView = findViewById(R.id.rv_course_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.taskAdapter = new TaskAdapter(this, this.tasks);
        recyclerView.setAdapter(this.taskAdapter);
    }

    private void initSectionView() {
        RecyclerView recyclerView = findViewById(R.id.rv_section_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.sectionAdapter = new SectionAdapter(this, this.sections);
        recyclerView.setAdapter(this.sectionAdapter);
    }

    public void onAddSectionClicked(View view) {
        //TODO add Section when button clicked
    }
}
