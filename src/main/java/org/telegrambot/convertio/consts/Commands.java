package org.telegrambot.convertio.consts;

/**
 * В этом классе содержатся все константы этого приложения
 */
public class Commands {

    // commands
    // -- user
    public static final String START = "/start";
    public static final String DEALS = "/deals";
    public static final String CHANNELS = "/channels";
    public static final String ACCOUNT = "/account";
    public static final String SETTINGS = "/settings";
    public static final String INFO = "/info";
    public static final String SEARCH_FILE_TYPE = "/search";


    // -- admin
    public static final String GET_MY_CHAT_ID = "/getMyChatId";
    public static final String GET_MONTHLY_STATISTICS = "/monthly_statistics";

    // -- common
    // info
    public static final String CONFIRM = "CONFIRM";
    public static final String CANCEL = "CANCEL";
    public static final String CURRENCIES_LIMITS_COMMAND = "CURRENCIES_LIMITS_COMMAND";

    // language
    public static final String LANGUAGE_EN = "/english";
    public static final String LANGUAGE_RU = "/russian";

    // deals
    public static final String SELECT_CURRENCY_DEAL = "SELECT_CURRENCY_DEAL";
    public static final String SELECT_CURRENCY_WITHDRAW = "SELECT_CURRENCY_WITHDRAW";
    public static final String SELECT_CURRENCY_RECEIVE = "SELECT_CURRENCY_RECEIVE";
    public static final String SELECT_CURRENCY_REPLENISH = "SELECT_CURRENCY_REPLENISH";

    // search user handler
    public static final String CANCEL_USER_SEARCH_COMMAND = "CANCEL_USER_SEARCH_COMMAND";
    public static final String START_DEAL_COMMAND = "START_DEAL_COMMAND";

    // post commands
    public static final String CREATE_NEW_CHANNEL_AD = "CREATE_NEW_CHANNEL_AD";
    public static final String CONFIRM_CREATE_NEW_CHANNEL_AD = "CONFIRM_CREATE_NEW_CHANNEL_AD";
    public static final String CANCEL_CREATE_NEW_CHANNEL_AD = "CANCEL_CREATE_NEW_CHANNEL_AD";
    public static final String MY_ADS_COMMAND = "MY_ADS_COMMAND";
    public static final String ALL_ADS_COMMAND = "ALL_ADS_COMMAND";
    public static final String ALL_ADS_SEARCH = "ALL_ADS_SEARCH";
    public static final String BUY_CHANNEL = "buy_channel";
    public static final String SKIP = "SKIP";
    public static final String CONFIRM_SET_SEARCH_OPTIONS = "CONFIRM_SET_SEARCH_OPTIONS";
    public static final String CONFIRM_SET_FILTERS = "CONFIRM_SET_FILTERS";
    public static final String CONFIRM_SET_COSTS = "CONFIRM_SET_COSTS";
    public static final String CONFIRM_SET_SUBSCRIBERS = "CONFIRM_SET_SUBSCRIBERS";
    public static final String CONFIRM_SET_VIEWS = "CONFIRM_SET_VIEWS";
    public static final String CONFIRM_SET_TAGS = "CONFIRM_SET_TAGS";
    public static final String CONFIRM_SET_FIELDS = "CONFIRM_SET_FIELDS";
    public static final String DELETE_POST = "DELETE_POST";
    public static final String UPDATE_POST = "UPDATE_POST";
    public static final String EDIT_POST = "EDIT_POST";
    public static final String CONFIRM_CHOOSE_EDIT_AD_FIELDS = "CONFIRM_CHOOSE_EDIT_FIELDS";
    public static final String CONFIRM_EDIT_AD = "CONFIRM_EDIT_AD";
    public static final String SHOW_CHANNELS_BY_TOPICS = "SHOW_CHANNELS_BY_TOPICS";
    public static final String CHANNEL_TOPIC = "CHANNEL_TOPIC";

    // filter
    public static final String CATEGORY = "CATEGORY";

    // post commands #update


    // trade commands
    public static final String I_TRANSFERRED_CHANNEL_COMMAND = "I_TRANSFERRED_CHANNEL_COMMAND";
    public static final String INTERRUPT_DEAL_COMMAND = "INTERRUPT_DEAL_COMMAND";
    public static final String CONFIRM_INTERRUPT_DEAL_COMMAND = "CONFIRM_INTERRUPT_DEAL_COMMAND";
    public static final String CHECK_IF_CHANNEL_ADDED = "CHECK_IF_CHANNEL_ADDED";
    public static final String CANCEL_TRADE_COMMAND = "CANCEL_TRADE_COMMAND";
    public static final String CANCEL_INTERRUPT_COMMAND = "CANCEL_INTERRUPT_COMMAND";
    public static final String CHOOSE_DEAL_CURRENCY = "CHOOSE_DEAL_CURRENCY";
    public static final String CONFIRM_TRADE_COMMAND = "CONFIRM_TRADE_COMMAND";
    public static final String FREEZE_TRADE_COMMAND = "FREEZE_TRADE_COMMAND";
    public static final String UNFREEZE_TRADE_COMMAND = "UNFREEZE_TRADE_COMMAND";


    // payments
    // -- crypto wallets
    public static final String CREATE_CRYPTO_WALLET = "CREATE_CRYPTO_WALLET";
    public static final String CHOOSE_CRYPTO_TO_CREATE = "CHOOSE_CRYPTO_TO_CREATE";
    // -- withdraw
    public static final String APPROVE_WITHDRAW = "APPROVE_WITHDRAW";
    public static final String CANCEL_WITHDRAW = "CANCEL_WITHDRAW";

    // -- replenish
    public static final String APPROVE_REPLENISH = "APPROVE_REPLENISH";
    public static final String CANCEL_REPLENISH = "CANCEL_REPLENISH";

    // -- stop
    public static final String STOP_OPERATION = "STOP_OPERATION";

    // my profile
    public static final String WITHDRAW_MONEY = "WITHDRAW_MONEY";
    public static final String REPLENISH_MONEY = "REPLENISH_MONEY";
    public static final String REVOKE_ID = "REVOKE_ID";

    // deals
    public static final String CHANGE_PAGE = "CHANGE_PAGE";
    public static final String GET_DEAL = "GET_DEAL";
    public static final String GET_AD = "GET_AD";
    public static final String BACK_TO_DEAL_LIST = "BACK_TO_DEAL_LIST";
    public static final String NULL = "null";
    // deal
    public static final String DELETE_DEAL = "DELETE_DEAL";

    // undo
    public static final String UNDO = "UNDO";

    // unknown request
    public static final String NO_SUCH_COMMAND = "NO_SUCH_COMMAND";


}

