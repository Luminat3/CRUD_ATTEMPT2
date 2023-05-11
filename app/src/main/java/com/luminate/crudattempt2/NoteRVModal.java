package com.luminate.crudattempt2;

import android.os.Parcel;
import android.os.Parcelable;


public class NoteRVModal implements Parcelable{
    private String NoteName;
    private String NoteDescription;
    private String NoteImg;
    private String NoteLink;
    private String NoteId;

    public String getNoteId() {
        return NoteId;
    }

    public void setCourseId(String NoteId) {
        this.NoteId = NoteId;
    }

    public NoteRVModal() {

    }

    protected  NoteRVModal(Parcel in)
    {
        NoteName = in.readString();
        NoteId = in.readString();
        NoteDescription = in.readString();
        NoteImg = in.readString();
        NoteLink = in.readString();
    }

    public static final Creator<NoteRVModal> CREATOR = new Creator<NoteRVModal>()
    {
        @Override
        public NoteRVModal createFromParcel(Parcel in) {
            return new NoteRVModal(in);
        }

        @Override
        public NoteRVModal[] newArray(int size) {
            return new NoteRVModal[size];
        }
    };

    public String getNoteName() {
        return NoteName;
    }

    public void setNoteName(String noteName) {
        NoteName = noteName;
    }

    public String getNoteDescription() {
        return NoteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        NoteDescription = noteDescription;
    }

    public String getNoteImg() {
        return NoteImg;
    }

    public void setNoteImg(String noteImg) {
        NoteImg = noteImg;
    }

    public String getNoteLink() {
        return NoteLink;
    }

    public void setNoteLink(String noteLink) {
        NoteLink = noteLink;
    }

    public NoteRVModal(String noteName, String noteDescription, String noteImg, String noteLink, String noteId) {
        NoteName = noteName;
        NoteDescription = noteDescription;
        NoteImg = noteImg;
        NoteLink = noteLink;
        NoteId = noteId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(NoteName);
        dest.writeString(NoteId);
        dest.writeString(NoteDescription);
        dest.writeString(NoteLink);
        dest.writeString(NoteImg);
    }
}
