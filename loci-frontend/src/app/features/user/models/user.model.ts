
export interface PersonalProfile {
  firstname: string,
  lastname: string,
  emailAddress: string,
  username: string,
  profilePictureUrl: string,
  activityStatus: boolean;

  privacy: {
    lastSeen: 'Everyone' | 'Contacts Only' | 'Nobody';
    friendRequests: 'Everyone' | 'Friends of Friends' | 'Nobody';
    profileVisibility: boolean;
  };
}


export interface PublicProfile {
  fullname: string,
  username: string,
  lastname: string,
  emailAddress: string,
  profilePictureUrl: string,
}


export interface ProfileUpdateRequest {
  firstname: string | null;
  lastname: string | null;
  emailAddress: string | null;
  profilePictureUrl: string | null;
  activityStatus: boolean | null;
  lastSeen: string | null;
  friendRequests: string | null;        // probably not user-editable | null?
  profileVisibility: boolean | null;
}

