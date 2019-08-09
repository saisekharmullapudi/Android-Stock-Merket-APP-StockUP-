package com.example.saisekhar.finalproject1


import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import com.example.saisekhar.finalproject1.R.id.cam
import com.example.saisekhar.finalproject1.R.id.selectphoto_button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_signup.*
import org.json.JSONObject
import java.time.Year
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
public class User ( val uid : String , val username : String , val useremail : String , val profileImageUrl : String )
{
    constructor () : this ("", "", "", "")
}

class SignupFragment : Fragment() {
    private var param1 : String ? = null
    private var param2 : String ? = null
    private var listener : OnFragmentInteractionListener ? = null
    private val CAMERA_REQUEST = 1888
    lateinit var c:Calendar
    lateinit var dp:DatePickerDialog



    override fun onCreate ( savedInstanceState : Bundle ?) {
        super . onCreate ( savedInstanceState )
        arguments ?. let {
            param1 = it. getString ( ARG_PARAM1 )
            param2 = it. getString ( ARG_PARAM2 )
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated (view : View, savedInstanceState : Bundle ?) {
        super . onViewCreated (view , savedInstanceState )
        if (! param1 !!. isEmpty () )
            email_register.text = Editable.Factory.getInstance().newEditable(param1)
        if (! param2 !!. isEmpty () )
            password_register.text = Editable.Factory.getInstance().newEditable( param2 )
        register_button. setOnClickListener {
            performRegister ()
        }
        already_have_account.setOnClickListener {

            Log .d(" SignUp ", " Try to show login activity ")
            listener !!. onSignInRoutine ()
        }
//        select_calender.setOnClickListener {
//            /*c= Calendar.getInstance()
//            var day:Int=c.get(Calendar.DAY_OF_MONTH)
//            var month:Int=c.get(Calendar.MONTH)
//            var year:Int=c.get(Calendar.YEAR)
//            var mDateDetListner: DatePickerDialog.OnDateSetListener? = null
////            var datePicker:DatePicker=DatePicker(SignupFragment().context)
//            dp=DatePickerDialog(SignupFragment().context,android.R.style.TextAppearance_Theme_Dialog,mDateDetListner,year,month,day);
//            dp.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            dp.show()
//            mDateDetListner=DatePickerDialog.OnDateSetListener()
//            {
//                datePicker: DatePicker, i: Int, i1: Int, i2: Int ->dob.text=Editable.Factory.getInstance().newEditable(i.toString()+"/"+i1.toString()+"/"+i2.toString())
//            }*/
//        }
        cam.setOnClickListener {
            /*val REQUEST_IMAGE_CAPTURE = 1
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//            }*/
            var CAMERA_REQUEST :Int = 1888;
            val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, 1)
        }

        selectphoto_button .setOnClickListener {
            Log .d(" SignUp ", " Try to show photo selector ")
            val intent = Intent ( Intent . ACTION_PICK )
            intent.type = "image/*"
            startActivityForResult ( intent , 0)
        }
    }

    var selectedPhotoUri : Uri? = null
    override fun onActivityResult ( requestCode : Int , resultCode : Int , data : Intent ?) {
        super . onActivityResult ( requestCode , resultCode , data )
        if ( requestCode == 0 && resultCode == Activity . RESULT_OK && data != null ) {
            // proceed and check what the selected image was ....
            Log .d(" SignUp ", " Photo was selected ")
            selectedPhotoUri = data.data
//            val bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver , selectedPhotoUri )
//            selectphoto_imageview . setImageBitmap ( bitmap )
//            selectphoto_button . alpha = 0f // hide button for selected photo imageview
        }
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data!=null) {
            Log.i("camera data","data")

            selectedPhotoUri = data.data
        }
    }

    private fun performRegister () {
        val email = email_register . text . toString ()
        val password = password_register . text . toString ()
        if ( email . isEmpty () || password . isEmpty () ) {

            Toast . makeText ( context , " Please enter text in email /pw", Toast . LENGTH_SHORT ) . show ()
            return
        }
        if ( selectedPhotoUri == null ) {
            Toast . makeText ( context , " Please select your profile image ", Toast . LENGTH_SHORT ). show ()
            return
        }

        Log .d(" SignUp ", " Attempting to create user with email :$email ")
// Firebase Authentication to create a user with email and password
        FirebaseAuth . getInstance () . createUserWithEmailAndPassword (email ,
                password )
                . addOnCompleteListener {
                    if (! it. isSuccessful )
                        return@addOnCompleteListener

                    Log .d(" SignUp ", " Successfully created user with uid : ${it. result !!. user . uid }")
                    uploadImageToFirebaseStorage ()
                }
                . addOnFailureListener {
                    Log .d(" SignUp ", " Failed to create user :${it. message }")
                    Toast . makeText ( context , " Failed to create user :${it. message }", Toast . LENGTH_SHORT ). show ()
                }
    }
    private fun uploadImageToFirebaseStorage () {
        if ( selectedPhotoUri == null ) return
        val filename = UUID . randomUUID () . toString ()
        val ref = FirebaseStorage . getInstance () . getReference ("/images/$filename ")
        ref . putFile ( selectedPhotoUri !!). addOnSuccessListener {
            Log .d(" SignUp ", " Successfully uploaded image :${it. metadata ?. path }")
            ref . downloadUrl . addOnSuccessListener {
                Log .d(" SignUp ", " File Location : $it ")
                saveUserToFirebaseDatabase (it. toString () )
            }
        }
                . addOnFailureListener {
                    Log .d(" SignUp ", " Failed to upload image to storage : ${it. message }")
                }
    }
    private fun saveUserToFirebaseDatabase ( profileImageUrl : String )
    {
        val uid = FirebaseAuth . getInstance () . uid ?: ""
        var json:JSONObject= JSONObject()
        json.put("uid",uid)
        json.put("Avail_amount",1000.00)
        json.put("Spent_amount",0.00)
        val runnable = Runnable {
            //                details/jkcFD1klHzYaWtRHMEeh7zT9PS63
            var cont=Myapp.getContext()
            var base=cont.getString(R.string.url_base)
            val url = base+"add_user"
            MyUtility.sendHttPostRequest(url, json.toString())
        }
        Thread(runnable).start()
//        val ref = FirebaseDatabase.getInstance().getReference ("/users/$uid ")
//        val user = User (uid , username_register. text . toString () , email_register . text . toString () , profileImageUrl )
        /*Log.i("uservalue",uid.toString())
        ref.setValue(uid).addOnSuccessListener {
            Log.i("UploadedSuccessful","Succeesgul") }
                .addOnFailureListener{ Log.i("Upload Failed", "Failed") }*/
        val mDatabase : DatabaseReference = FirebaseDatabase.getInstance().reference
        val mRef = mDatabase.child("users")
        val user=Person(uid,username_firstname.text.toString(),
                username_lastname.text.toString(),
                email_register.text.toString(),
                password_register.text.toString(),
                dob.text.toString(),
                phone_number.text.toString(),
                SSN.text.toString(),
                address1.text.toString(),
                profileImageUrl,
                1000.00)
        mRef.child(uid).setValue(user).addOnSuccessListener {
            Log.i("user", user.toString())
            listener !!.Login()

        }.addOnFailureListener {
            Log.i("error","Failed")
        }
    }
    override fun onAttach ( context : Context) {
        super . onAttach ( context )
        if ( context is OnFragmentInteractionListener ) {
            listener = context
        } else {
            throw RuntimeException ( context . toString () + " must implement OnFragmentInteractionListener ")
        }
    }
    override fun onDetach () {
        super . onDetach ()
        listener = null
    }
    interface OnFragmentInteractionListener {
        fun onSignInRoutine ()
        fun Login()
    }
    companion object {
        @JvmStatic
        fun newInstance ( param1 : String , param2 : String ) =
                SignupFragment () . apply {
                    arguments = Bundle () . apply {
                        putString ( ARG_PARAM1 , param1 )
                        putString ( ARG_PARAM2 , param2 )
                    }
                }
    }
}

