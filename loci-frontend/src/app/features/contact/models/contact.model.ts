
export interface UserSearchItem {
  id: string;
  name: string;
  username: string;
  email: string;
  avatarSeed: string;
  friendshipStatus: 'none' | 'pending' | 'friends';
}
