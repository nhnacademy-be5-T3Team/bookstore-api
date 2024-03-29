    package com.t3t.bookstoreapi.payment.controller;

    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.t3t.bookstoreapi.payment.entity.TossPayments;
    import com.t3t.bookstoreapi.payment.responce.TossPaymentResponse;
    import com.t3t.bookstoreapi.payment.service.TossPaymentService;
    import org.json.simple.JSONObject;
    import org.json.simple.parser.JSONParser;
    import org.json.simple.parser.ParseException;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.stereotype.Controller;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestMethod;

    import javax.servlet.http.HttpServletRequest;
    import java.io.*;
    import java.net.HttpURLConnection;
    import java.net.URL;
    import java.nio.charset.StandardCharsets;
    import java.util.Base64;


    @Controller
    public class WidgetController {

        @RequestMapping(value = "/", method = RequestMethod.GET)
        public String index(HttpServletRequest request, Model model) throws Exception {
            return "/checkout";
        }
        @RequestMapping(value = "/success", method = RequestMethod.GET)
        public String paymentRequest(HttpServletRequest request, Model model, TossPaymentResponse tossPaymentResponse) throws Exception {
            String paymentKey = request.getParameter("paymentKey");
            String orderId = request.getParameter("orderId");
            String amount = request.getParameter("amount");

            model.addAttribute("paymentKey", paymentKey);
            model.addAttribute("orderId", orderId);
            model.addAttribute("amount", amount);
            //tossPaymentService.saveTossPayment(tossPaymentResponse);

            return "/success";
        }
        @RequestMapping(value = "/fail", method = RequestMethod.GET)
        public String failPayment(HttpServletRequest request, Model model) throws Exception {
            String failCode = request.getParameter("code");
            String failMessage = request.getParameter("message");

            model.addAttribute("code", failCode);
            model.addAttribute("message", failMessage);

            return "/fail";
        }
    }
