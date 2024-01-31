package com.example.color_my_world;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PHOTO extends Activity {
    public static Bitmap image;
    File f = null;
    public static String encodedImage = "", path = "";
    ///////
    private static final int CAMERA_CODE = 101, GALLERY_CODE = 201, CROPING_CODE = 301, READ_EXTERNAL_STORAGE_PERMISSION = 1, CAMERA = 2;
    static int flag2 = 3;
    private Button btn_select_image, tryimg;
    private ImageView imageView;
    private Uri mImageCaptureUri;
    private File outPutFile = null;
    private String imagename = "";
    Button b1;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo);


        try {
            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }


        // getPermissionToReadImageFromGallery();
        b1 = (Button) findViewById(R.id.buttontry);

        outPutFile = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
        btn_select_image = (Button) findViewById(R.id.btn_select_image);
        imageView = (ImageView) findViewById(R.id.img_photo);

        btn_select_image.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // CustomerHome.flag = 1;
                //  Imgcap.flag2 = 2;
                selectImageOption();

//		   
            }
        });

        b1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                Log.d("pearl", Categories.cat1);
////            	Toast.makeText(getApplicationContext(), Categories.cat1, Toast.LENGTH_LONG).show();
//                // TODO Auto-generated method stub
//                if (Categories.cat1.equalsIgnoreCase("Necklace")) {
                    Intent j = new Intent(getApplicationContext(), FitEarring.class);
                    startActivity(j);
//                } else if (Categories.cat1.equalsIgnoreCase("Earring")) {
//                    Intent j = new Intent(getApplicationContext(), FitEarring.class);
//                    startActivity(j);
//                } else if (Categories.cat1.equalsIgnoreCase("hat")) {
//                    Intent j = new Intent(getApplicationContext(), FitEarring.class);
//                    startActivity(j);
//                } else if (Categories.cat1.equalsIgnoreCase("Spects")) {
//                    Intent j = new Intent(getApplicationContext(), FitEarring.class);
//                    startActivity(j);
//                } else {
//                    Intent j = new Intent(getApplicationContext(), FitEarring.class);
//                    startActivity(j);
//                }
            }
        });
    }
//    private void getPermissionToReadImageFromGallery() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},READ_EXTERNAL_STORAGE_PERMISSION);
//            }
//            else {
//                Log.d("","We have Permission to load the Contacts");
//            }
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if(checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
//                requestPermissions(new String[]{Manifest.permission.CAMERA},CAMERA);
//                Toast.makeText(getApplicationContext(),"Camera premission added",Toast.LENGTH_LONG).show();
//            }
//            else {
//                Log.d("","We have Permission to load the Contacts");
//            }
//        }
//    }

    private void selectImageOption() {
        final CharSequence[] items = {/*"Capture Photo", */"Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(PHOTO.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Capture Photo")) {
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    Date date = new Date();
//                    DateFormat df = new SimpleDateFormat("-mm-ss");
//                    imagename = df.format(date) + ".jpg";
//                    f = new File(android.os.Environment.getExternalStorageDirectory(), imagename);
//                    mImageCaptureUri = Uri.fromFile(f);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
//                    startActivityForResult(intent, CAMERA_CODE);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Date date = new Date();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy-mm-ss");
                    imagename = df.format(date) + ".jpg";
                    f = new File(android.os.Environment.getExternalStorageDirectory(), imagename);
                    mImageCaptureUri = Uri.fromFile(f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                    startActivityForResult(intent, CAMERA_CODE);
                } else if (items[item].equals("Choose from Gallery")) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, GALLERY_CODE);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

            mImageCaptureUri = data.getData();
            System.out.println("Gallery Image URI: " + mImageCaptureUri);
            //   CropingIMG();

            try {

                Uri selectedImage = data.getData();
                path = getRealPathFromURI(selectedImage);
                File file = new File(path);
                //  atype=path.substring(path.lastIndexOf(".")+1)+"";


                image = decodeFile(path);
                byte[] byteArray = null;
                try {
                    InputStream inputStream = new FileInputStream(file);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] b = new byte[2048 * 8];
                    int bytesRead = 0;

                    while ((bytesRead = inputStream.read(b)) != -1) {
                        bos.write(b, 0, bytesRead);
                    }

                    byteArray = bos.toByteArray();
                    Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    imageView.setImageBitmap(bm);
                } catch (IOException e) {

                    Log.d("=err====", e.getMessage() + "");
                    Toast.makeText(this, "String :" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }


                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
            } catch (Exception e) {

            }
        } else if (requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK) {

            System.out.println("Camera Image URI : " + mImageCaptureUri);
            //  CropingIMG();

            path = f.getAbsolutePath();

            // image = BitmapFactory.decodeFile(path);
            image = decodeFile(path); //sha corrected
            imageView.setImageBitmap(image);

            try {

                int ln = (int) f.length();
                byte[] byteArray = null;

                InputStream inputStream = new FileInputStream(f);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[ln];
                int bytesRead = 0;

                while ((bytesRead = inputStream.read(b)) != -1) {
                    bos.write(b, 0, bytesRead);
                }
                inputStream.close();
                byteArray = bos.toByteArray();

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;

            } catch (Exception e) {

            }


        } else if (requestCode == CROPING_CODE) {

            try {
                if (outPutFile.exists()) {
                    System.out.println("sha in intent result for crop");
                    path = f.getAbsolutePath();


                } else {
                    Toast.makeText(getApplicationContext(), "Error while save image", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

            }
        }
    }

    public void alrtdailog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("");
        builder.setMessage("");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                CropingIMG();
            }
        });
        builder.setNegativeButton("ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void CropingIMG() {
        System.out.println("sha in cropimg");
        @SuppressWarnings("rawtypes") final ArrayList<Object> cropOptions = new ArrayList<Object>();

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        intent.setType("*/*");

        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
        int size = list.size();
        if (size == 0) {
            Toast.makeText(this, "Cann't find image croping app", Toast.LENGTH_SHORT).show();
            return;
        } else {
            intent.setData(mImageCaptureUri);
            intent.putExtra("outputX", 512);
            intent.putExtra("outputY", 512);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);

            //TODO: don't use return-data tag because it's not return large image data and crash not given any message
            //intent.putExtra("return-data", true);

            //Create output file here
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outPutFile));

            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = (ResolveInfo) list.get(0);

                i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                startActivityForResult(i, CROPING_CODE);
            }

        }
    }

    private Bitmap decodeFile(File f) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inPreferredConfig = Bitmap.Config.RGB_565;
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 512;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inPreferredConfig = Bitmap.Config.RGB_565;
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    public Bitmap decodeFile(String path) {//you can provide file path here
        int orientation;
        try {
            if (path == null) {
                return null;
            }
            // decode image size
            //    BitmapFactory.Options o = new BitmapFactory.Options();
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inPreferredConfig = Bitmap.Config.RGB_565;
            o.inJustDecodeBounds = true;
            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 0;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale++;
            }
            // decode with inSampleSize
            //  BitmapFactory.Options o2 = new BitmapFactory.Options();
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inPreferredConfig = Bitmap.Config.RGB_565;
            o2.inSampleSize = scale;
            Bitmap bm = BitmapFactory.decodeFile(path, o2);
            Bitmap bitmap = bm;

            ExifInterface exif = new ExifInterface(path);

            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

            Log.e("ExifInteface .........", "rotation =" + orientation);

            //exif.setAttribute(ExifInterface.ORIENTATION_ROTATE_90, 90);

            Log.e("orientation", "" + orientation);
            Matrix m = new Matrix();

            if ((orientation == ExifInterface.ORIENTATION_ROTATE_180)) {
                m.postRotate(180);
                //m.postScale((float) bm.getWidth(), (float) bm.getHeight());
                // if(m.preRotate(90)){
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                m.postRotate(90);
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                m.postRotate(270);
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
                return bitmap;
            }
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String path;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null)
            path = contentURI.getPath();

        else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            path = cursor.getString(idx);

        }
        if (cursor != null)
            cursor.close();
        return path;
    }
}