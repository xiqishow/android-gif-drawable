package pl.droidsonroids.gif;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.droidsonroids.gif.test.R;

import static org.hamcrest.CoreMatchers.containsString;

public class GifDrawableExceptionTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	private GifDrawable gifDrawable;

	@Before
	public void setUp() throws Exception {
		final Resources resources = InstrumentationRegistry.getContext().getResources();
		gifDrawable = new GifDrawable(resources, R.raw.test);
	}

	@After
	public void tearDown() {
		gifDrawable.recycle();
	}

	@Test
	public void frameIndexOutOfBoundsMessageContainsRange() throws Exception {
		final int numberOfFrames = gifDrawable.getNumberOfFrames();
		final int invalidFrameIndex = numberOfFrames + 10;
		expectedException.expectMessage(containsString(Integer.toString(numberOfFrames)));
		expectedException.expect(IndexOutOfBoundsException.class);
		gifDrawable.getFrameDuration(invalidFrameIndex);
	}

	@Test
	public void exceptionThrownWhenPixelsArrayTooSmall() throws Exception {
		expectedException.expect(ArrayIndexOutOfBoundsException.class);
		gifDrawable.getPixels(new int[0]);
	}

	@Test
	public void exceptionThrownWhenPixelCoordinateXOutOfRange() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		gifDrawable.getPixel(gifDrawable.getIntrinsicWidth(), 0);
	}

	@Test
	public void exceptionThrownWhenPixelCoordinateYOutOfRange() throws Exception {
		expectedException.expect(IllegalArgumentException.class);
		gifDrawable.getPixel(0, gifDrawable.getIntrinsicHeight());
	}
}
