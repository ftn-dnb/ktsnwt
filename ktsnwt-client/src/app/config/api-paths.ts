const API_BASE = 'http://localhost:8080';

export const API_LOGIN = API_BASE + '/auth/login';
export const API_REGISTER_USER = API_BASE + '/api/users/public/add-user';
export const API_VERIFY_ACCOUNT = API_BASE + '/api/users/public/verify-account';
export const API_MY_PROFILE = API_BASE + '/api/users/my-profile';
export const API_USER_PASSWORD_CHANGE = API_BASE + '/auth/change-password';
export const API_USER_IMAGE = API_BASE + '/api/images/users';
export const API_LOCATIONS_ALL = API_BASE + "/api/locations/all";
export const API_EVENTS = API_BASE + '/api/event/all'; // TODO: ADD LATER /public/
