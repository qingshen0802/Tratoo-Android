package br.com.tratto.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lily on 8/25/17.
 */

public class Profile {

    public int id;
    @SerializedName("user_id")
    public int userId;
    @SerializedName("type")
    public String type;
    @SerializedName("full_name")
    public String fullName = "";
    @SerializedName("document_id_number")
    public String documentIdNumber;
    @SerializedName("email")
    public String email;
    @SerializedName("username")
    public String userName = "";
    @SerializedName("latitude")
    public double latitude;
    @SerializedName("longitude")
    public double longitude;
    @SerializedName("photo_url")
    public String photoUrl;
    @SerializedName("cover_photo_url")
    public String coverPhotoUrl;
    @SerializedName("company_category_name")
    public String companyCategoryName;
    @SerializedName("current_document_status")
    public String currentDocumentStatus;
    @SerializedName("bank_account_id")
    public int bankAccountId;
    @SerializedName("referral_link")
    public String referralLink;
    @SerializedName("address")
    public String address;
    @SerializedName("credits_amount")
    public double creditAmount;
    @SerializedName("distance")
    public String distance;
    @SerializedName("url")
    public String url;
    @SerializedName("discount_club_active")
    public boolean discountClubActive;
    @SerializedName("discount_club_bonus_amount")
    public double discountClubBonusAmount;

    //Company
    @SerializedName("phone_number")
    public String phoneNumber = "";
    @SerializedName("description")
    public String description = "";

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", userId=" + userId +
                ", type='" + type + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
