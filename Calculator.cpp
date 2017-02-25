#include <iostream>
#include <sstream>
#include <string>
#include <vector>
#include <stack>
#include <queue>

using std::cout;
using std::endl;
using std::cin;
using std::string;
using std::vector;
using std::stack;

string calculator(const string &a, const string &b, const string &sym)
{
    string result;
    double x = 0;
    double y = 0;
    try {
        x = stod(a);
        y = stod(b);
    }catch (std::invalid_argument err) {
        result += a;
        result += sym;
        result += b;
        return result;
    }
    switch (sym[0]) {
        case '+':
            result = std::to_string(x + y);
            break;
        case '-':
            result = std::to_string(x - y);
            break;
        case '*':
            result = std::to_string(x * y);
            break;
        case '/':
            result = std::to_string(x / y);
            break;
        default:
            break;
        }
    return result;
}
bool is_symbol(const string &sym)
{
    switch (sym[0]) {
        case '+':
            return true;
        case '-':
            return true;
        case '*':
            return true;
        case '/':
            return true;
        default:
            return false;
    }
    return false;
}

unsigned get_pri(const string &sym)
{
    switch (sym[0]) {
        case '+':
            return 1;
        case '-':
            return 1;
        case '*':
            return 2;
        case '/':
            return 2;
        default:
            return 0;
    }
    return 0;
}
string calculation(const vector<string> &formula)
{
    stack<string> deal_data;
    string a, b;
    for (auto data : formula) {
        if (is_symbol(data)) {
            b = deal_data.top();
            deal_data.pop();
            a = deal_data.top();
            deal_data.pop();
            deal_data.push(calculator(a, b, data));
        }
        else {
            deal_data.push(data);
        }
    }
    return deal_data.top();
}

string calculation(stack<string> &formula)
{
    vector<string> sorded;
    stack<string> cal_symbol;
    for (unsigned priority = 0;
         !formula.empty();
         formula.pop()) {
        if (is_symbol(formula.top())) {
            if (get_pri(formula.top()) >= priority) {
                cal_symbol.push(formula.top());
                priority = get_pri(formula.top());
            }
            else {
                while (!cal_symbol.empty()) {
                    sorded.push_back(cal_symbol.top());
                    cal_symbol.pop();
                }
                cal_symbol.push(formula.top());
                priority = get_pri(formula.top());
            }
        }
        else {
            sorded.push_back(formula.top());
        }
    }

    while (!cal_symbol.empty()) { 
        sorded.push_back(cal_symbol.top());
        cal_symbol.pop();
    }
    return calculation(sorded);;
}

string interpreter(const vector<string> &formula)
{
    stack<string> deal_data;
    for (auto data : formula) {
        if (data == ")") {
            stack<string> tmp;
            while (!deal_data.empty() && deal_data.top() != "(") {
                tmp.push(deal_data.top());
                deal_data.pop();
            }
            deal_data.pop();
            deal_data.push(calculation(tmp));
        }
        else {
            deal_data.push(data);
        }
    }
    stack<string> result;
    while (!deal_data.empty()) {
        result.push(deal_data.top());
        deal_data.pop();
    }
    deal_data.push(calculation(result));
    return deal_data.top();
}

void read(std::istream &input, vector<string> &formula)
{
    string tmp, sym;
    getline(input, tmp);
    std::istringstream line(tmp);
    while (line >> sym) {
        formula.push_back(sym);
    }
}

int main()
{
    vector<string> formula;
    string result;
    read(cin, formula);
    result = interpreter(formula);
    //result = calculation(formula);
    cout <<  result << endl;
}
