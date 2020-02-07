const API_BASE = 'http://localhost:8080';

// User
export const API_LOGIN = API_BASE + '/auth/login';
export const API_REGISTER_USER = API_BASE + '/api/users/public/add-user';
export const API_VERIFY_ACCOUNT = API_BASE + '/api/users/public/verify-account';
export const API_MY_PROFILE = API_BASE + '/api/users/my-profile';
export const API_USER_PASSWORD_CHANGE = API_BASE + '/auth/change-password';
export const API_USER_IMAGE = API_BASE + '/api/images/users';

// Locations
export const API_LOCATIONS_ALL = API_BASE + '/api/locations/all';
export const API_LOCATION_ADD = API_BASE + '/api/locations';
export const API_LOCATION_GET_ID = API_BASE + '/api/locations';
export const API_LOCATION = API_BASE + '/api/locations';
export const API_LOCATION_GET_NAME = API_LOCATION + '/name/';
export const API_LOCATION_GET_NAMES = API_LOCATION + '/names/all';
export const API_LOCATION_ADDRESS = API_BASE + '/api/locations/address';

// Halls
export const API_HALL_GET = API_BASE + '/api/halls';

// Sectors
export const API_SECTOR = API_BASE + '/api/sectors';

// Events
export const API_EVENTS = API_BASE + '/api/event/public/all';
export const API_EVENTS_ADD = API_BASE + '/api/event/addEvent';
export const API_EVENTS_IMAGE = API_BASE + '/api/images/events/';
export const API_EVENTS_GET_ONE_BY_ID = API_BASE + '/api/event/public/';
export const API_EVENT_SET_PRICING = API_BASE + '/api/event/addPricing/';
export const API_EVENT_DAY_DISABBLE = API_BASE + '/api/eventDay/disable/';
export const API_EVENT_EDIT = API_BASE + '/api/event/editEvent';
export const API_EVENT_DAY_DESCRIPTION = API_BASE + '/api/eventDay/description';
export const API_EVENT_SEARCH = API_BASE + '/api/event/public/search';

// Tickets
export const API_MY_RESERVATIONS = API_BASE + '/api/tickets';
export const API_BUY_TICKET = API_BASE + '/api/tickets/buy';
export const API_EVENT_DAILY_REPORT = API_BASE + '/api/tickets/eventDailyReport/';
export const API_LOCATION_DAILY_REPORT = API_BASE + '/api/tickets/locationDailyReport/';
export const API_LOCATION_MONTHLY_REPORT = API_BASE + '/api/tickets/locationMonthlyReport/';
export const API_CANCEL_TICKET = API_BASE + '/api/tickets/cancel';
export const API_GET_TICKETS_BY_EVENT_DAY = API_BASE + '/api/tickets/event-day-id/';
export const API_RESERVE_TICKETS = API_BASE + '/api/tickets/reserve';
