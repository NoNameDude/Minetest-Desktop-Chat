local ie = minetest.request_insecure_environment()
--Path to your filename.txt!
local file_path = ""
--path to your filename2.txt!
local file_path2 = ""
local relay = {
    ["messages"] = {},
    --["Terminal"] = {}
}

function send_message_to_terminal(msg)
    table.insert(relay["messages"], msg)   
end
 
--Get input string from terminal
local function get_recent_post()
    local java_file = ie.io.open(file_path, "r")
    if java_file:seek("end") == 0 then
        java_file:close()
        return 
    end
    java_file:close() --dont like this line pre-fix
    local output = false
    for line in ie.io.lines(file_path) do
        minetest.chat_send_all(minetest.colorize("#4dffe4", line))
        output = true 
    end 
    --erase whatever was inside that file if output returned a full string
    if output == true then
        java_file = ie.io.open(file_path, "w") --dont like this line but pre-fix
        java_file:write("")
        java_file:close()
    end  
end 



local timer = 0
minetest.register_globalstep(function(dtime)
    timer = timer + dtime
    if timer >= tonumber(0.5) then
        get_recent_post()
        local msg_file = nil
        --does the table rly have a input?
        if relay["messages"][1] ~= nil then 
            msg_file = ie.io.open(file_path2, "a")
        end
    
        --make sure file is empty so it dosn't corrupt
        if msg_file ~= nil then
            if msg_file:seek("end") ~= 0 then
                msg_file:close()
                --make it nil so it dosnt corrupt with a future check
                msg_file = nil
            end
        end

        if msg_file ~= nil then 
            for i, message in pairs(relay["messages"]) do 
                msg_file:write(message.."\n") 
            end
        end

        --empty table for new input
        relay = {messages = {}}
        --close all open files and set timer to 0
        if msg_file ~= nil then
           msg_file:close() 
        end
        timer = 0
    end
end)  

--if you got a own chat mod remove minetest.register_on_chat_message and implement this code into yours
minetest.register_on_chat_message(function(name, message)
    local msg = "<"..name.."> "..message
    minetest.chat_send_all(msg)
    send_message_to_terminal(msg)
    return true
end)
