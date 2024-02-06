//package org.nexchange.filter;
//
//import com.auth0.jwt.interfaces.DecodedJWT;
//import jakarta.annotation.Resource;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.nexchange.utils.JwtUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.annotation.AccessType;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//public class JwtAuthorizeFilter extends OncePerRequestFilter {
//    @Resource
//    JwtUtils jwtUtils;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authorization = request.getHeader("authorization");
//        DecodedJWT jwt = jwtUtils.resolveJwt(authorization);
//        if (jwt != null) {
//            UserDetails userDetails = jwtUtils.toUserDetails(jwt);
//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//
//        }
//
//    }
//}
