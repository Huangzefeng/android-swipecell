package com.lumbi.pulltoviewsample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lumbi.widgets.PullToView;
import com.lumbi.widgets.PullToView.PullToViewListener;

public class MainActivity extends Activity implements PullToViewListener {

	private PullToView pullToView;
	private TextView text;
	private View top;
	private View bottom;
	private View topIntensity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		pullToView = (PullToView) findViewById(R.id.pullToView);
		pullToView.setListener(this);
		
		text = (TextView) findViewById(R.id.text);
		text.setText("Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. Typi non habent claritatem insitam; est usus legentis in iis qui facit eorum claritatem. Investigationes demonstraverunt lectores legere me lius quod ii legunt saepius. Claritas est etiam processus dynamicus, qui sequitur mutationem consuetudium lectorum. Mirum est notare quam littera gothica, quam nunc putamus parum claram, anteposuerit litterarum formas humanitatis per seacula quarta decima et quinta decima. Eodem modo typi, qui nunc nobis videntur parum clari, fiant sollemnes in futurum.");
		text.setText(text.getText().toString() + text.getText());
		
		top = findViewById(R.id.top);
		bottom = findViewById(R.id.bottom);
		topIntensity = findViewById(R.id.topIntensity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTopPulling(float intensity) {
		topIntensity.setAlpha(intensity);
	}

	@Override
	public void onTopPulled() {
		top.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void onTopReleased() {
		top.setVisibility(View.GONE);
		topIntensity.setAlpha(0);
		Toast.makeText(this, "Top Released!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onBottomPulling(float intensity) {
//		pullToView.setBackgroundColor(Color.rgb(0, (int) (255*intensity), 0));
	}

	@Override
	public void onBottomPulled() {
		bottom.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void onBottomReleased() {
		bottom.setVisibility(View.GONE);
		Toast.makeText(this, "Bottom Released!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onPullCancelled() {
		pullToView.setBackgroundColor(Color.WHITE);
		top.setVisibility(View.GONE);
		bottom.setVisibility(View.GONE);
		topIntensity.setAlpha(0);
		Toast.makeText(this, "Cancelled!", Toast.LENGTH_SHORT).show();
	}
}
