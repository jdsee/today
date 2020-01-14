package com.mobila.project.today.model;

import android.content.Context;
import android.net.Uri;
import android.os.Parcel;

import androidx.core.content.FileProvider;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class AttachmentTest {


    @Test
    public void writeAndReadParcel_Test() {
        Parcel parcel = MockParcel.obtain();
        Context context = new MockContext().getMockedContext();
        File filePath = new File(context.getFilesDir(), "shared");
        Uri testFile = Uri.fromFile(filePath);
        Attachment attachment = new Attachment("testAttachment", testFile);
        attachment.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        Attachment fromParcel = Attachment.CREATOR.createFromParcel(parcel);
        assertEquals(attachment, fromParcel);
    }

}