package ru.squel.myrssreader;
import org.junit.Test;
import android.content.Context;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ru.squel.myrssreader.data.local.DataBaseHelper;

import static org.mockito.Mockito.*;

/**
 * Created by sq on 19.06.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DataBaseHelperTest {

    @Mock
    Context mMockContext;

    @LargeTest
    public static DataBaseHelper dbHelper = new DataBaseHelper(mMockContext);

}
